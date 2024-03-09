package com.kill4us_kob.backend.service.impl.user.bot;

import com.kill4us_kob.backend.mapper.BotMapper;
import com.kill4us_kob.backend.pojo.User;
import com.kill4us_kob.backend.pojo.bot;
import com.kill4us_kob.backend.service.impl.utils.UserDetailsImpl;
import com.kill4us_kob.backend.service.user.bot.AddService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class AddServiceImpl implements AddService {

    @Autowired
    private BotMapper botMapper;

    @Override
    public Map<String, String> add(Map<String, String> data) {
        UsernamePasswordAuthenticationToken authenticationToken =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();

        UserDetailsImpl loginUser = (UserDetailsImpl) authenticationToken.getPrincipal();

        User user = loginUser.getUser();

        Map<String, String> map = new HashMap<>();

        String title = data.get("title");
        String description = data.get("description");
        String content = data.get("content");

        if (title == null || title.isEmpty()) {
            map.put("error_message", "标题不能为空");
            return map;
        }
        if (title.length() > 100) {
            map.put("error_message", "标题长度过长");
            return map;
        }

        if (description == null || description.isEmpty()) {
            description = "这个用户很懒，什么也没留下";

        }

        if (description.length() > 300) {
            map.put("error_message", "描述长度过长");
            return map;
        }

        if (content == null || content.isEmpty()) {
            map.put("error_message", "代码不能为空");
            return map;
        }

        if (content.length() > 10000) {
            map.put("error_message", "代码长度过长");
            return map;
        }

        Date now = new Date();
        bot bot = new bot(null, user.getId(), title, description, content, now, now);

        botMapper.insert(bot);
        map.put("error_message", "创建成功");

        return map;

    }
}
