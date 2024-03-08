package com.kill4us_kob.backend.service.impl.user.bot;

import com.kill4us_kob.backend.mapper.BotMapper;
import com.kill4us_kob.backend.pojo.User;
import com.kill4us_kob.backend.pojo.bot;
import com.kill4us_kob.backend.service.impl.utils.UserDetailsImpl;
import com.kill4us_kob.backend.service.user.bot.UpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
@Service
public class UpdateServiceImpl implements UpdateService {
    @Autowired
    private BotMapper botMapper;

    @Override
    public Map<String, String> update(Map<String, String> data) {
        UsernamePasswordAuthenticationToken authenticationToken =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();

        UserDetailsImpl loginUser = (UserDetailsImpl) authenticationToken.getPrincipal();
        User user = loginUser.getUser();

        int bot_id = Integer.parseInt(data.get("bot_id"));
        String title = data.get("title");
        String description = data.get("description");
        String content = data.get("content");

        Map<String, String> map = new HashMap<>();

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

        bot bot = botMapper.selectById(bot_id);

        if (bot == null) {
            map.put("error_message", "bot不存在，无法更新");
            return map;
        }

        if (!bot.getUserId().equals(user.getId())) {
            map.put("error_message", "你没有权限更改不属于你的bot");
            return map;
        }

        bot new_bot = new bot(
                bot.getId(),
                user.getId(),
                title,
                description,
                content,
                bot.getRating(),
                bot.getCreateTime(),
                new Date()
        );

        botMapper.updateById(new_bot);

        map.put("error_message", "修改bot成功");
        return map;
    }
}
