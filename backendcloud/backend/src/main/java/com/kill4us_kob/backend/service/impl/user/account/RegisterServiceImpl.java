package com.kill4us_kob.backend.service.impl.user.account;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kill4us_kob.backend.mapper.UserMapper;
import com.kill4us_kob.backend.pojo.User;
import com.kill4us_kob.backend.service.user.account.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service
public class RegisterServiceImpl implements RegisterService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Map<String, String> register(String username, String password, String confirmedPassword) {
        Map<String, String> map = new HashMap<>();
        if (username == null) {
            map.put("error_message", "username can't be empty!");
            return map;
        }
        if (password == null || confirmedPassword == null) {
            map.put("error_message", "password can't be empty");
            return map;
        }

        username = username.trim();
        if (username.isEmpty()) {
            map.put("error_message", "username can't be empty!");
            return map;
        }

        if (password.isEmpty() || confirmedPassword.isEmpty()) {
            map.put("error_message", "password can't be empty!");
            return map;
        }

        if (username.length() > 100) {
            map.put("error_message", "username is too long!");
            return map;
        }

        if (password.length() > 100 || confirmedPassword.length() > 100) {
            map.put("error_message", "password is too long!");
            return map;
        }

        if (!password.equals(confirmedPassword)) {
            map.put("error_message", "password and confirmedpassword is different!");
            return map;
        }

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        List<User> users = userMapper.selectList(queryWrapper);
        if (!users.isEmpty()) {
            map.put("error_message", "username is already exists");
            return map;
        }

        String encodedPassword = passwordEncoder.encode(password);
        String photo = "https://cdn.acwing.com/media/user/profile/photo/314027_lg_1e93722aee.jpg";
        User user = new User(null, username, encodedPassword, photo, 1500);
        userMapper.insert(user);

        map.put("error_message", "success");
        return map;


    }
}
