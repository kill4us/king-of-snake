package com.kill4us_kob.backend.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kill4us_kob.backend.mapper.UserMapper;
import com.kill4us_kob.backend.pojo.User;
import com.kill4us_kob.backend.service.ranklist.RanklistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RanklistServiceImpl implements RanklistService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public JSONObject getRanklist(Integer page) {
        IPage<User> userIPage = new Page<>(page, 3);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("rating");  //  降序排序
        List<User> users = userMapper.selectPage(userIPage, queryWrapper).getRecords();
        JSONObject resp = new JSONObject();
        for (User user:users) {
            user.setPassword("");
        }

        resp.put("users", users);
        resp.put("users_counts", userMapper.selectCount(null));

        return resp;
    }
}
