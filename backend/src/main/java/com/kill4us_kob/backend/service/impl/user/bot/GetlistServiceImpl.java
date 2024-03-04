package com.kill4us_kob.backend.service.impl.user.bot;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kill4us_kob.backend.mapper.BotMapper;
import com.kill4us_kob.backend.pojo.User;
import com.kill4us_kob.backend.pojo.bot;
import com.kill4us_kob.backend.service.impl.utils.UserDetailsImpl;
import com.kill4us_kob.backend.service.user.bot.GetlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetlistServiceImpl implements GetlistService {
    @Autowired
    private BotMapper botMapper;

    @Override
    public List<bot> getList() {
        UsernamePasswordAuthenticationToken authenticationToken =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();

        UserDetailsImpl loginUser = (UserDetailsImpl) authenticationToken.getPrincipal();
        User user = loginUser.getUser();

        QueryWrapper<bot> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", user.getId());

        return botMapper.selectList(queryWrapper);

    }
}
