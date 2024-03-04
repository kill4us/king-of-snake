package com.kill4us_kob.backend.service.impl.user.bot;

import com.kill4us_kob.backend.mapper.BotMapper;
import com.kill4us_kob.backend.pojo.User;
import com.kill4us_kob.backend.pojo.bot;
import com.kill4us_kob.backend.service.impl.utils.UserDetailsImpl;
import com.kill4us_kob.backend.service.user.bot.RemoveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class RemoveServiceImpl implements RemoveService {

    @Autowired
    private BotMapper botMapper;

    @Override
    public Map<String, String> remove(Map<String, String> data) {
        UsernamePasswordAuthenticationToken authenticationToken =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();

        UserDetailsImpl loginUser = (UserDetailsImpl) authenticationToken.getPrincipal();

        User user = loginUser.getUser();

        int bot_id = Integer.parseInt(data.get("bot_id"));
        bot bot = botMapper.selectById(bot_id);

        Map<String, String> map = new HashMap<>();
        if (bot == null) {
            map.put("error_message", "bot不存在");
            return map;
        }
        if (!bot.getUserId().equals(user.getId())) {
            map.put("error_message", "这个bot不是你的，没有权限删除");
            return map;
        }
        botMapper.deleteById(bot_id);
        map.put("error_message", "成功删除bot");

        return map;
    }
}
