package com.kill4us_kob.matchingsystem.service.impl;

import com.kill4us_kob.matchingsystem.service.MatchingService;
import com.kill4us_kob.matchingsystem.service.impl.utils.MatchingPool;
import org.springframework.stereotype.Service;

@Service
public class MatchingServiceImpl implements MatchingService {

    public static final MatchingPool matchingPool = new MatchingPool();

    @Override
    public String addPlayer(Integer userId, Integer rating, Integer botId) {
        System.out.println("add player" + userId + " " + rating);
        matchingPool.addPlayer(userId, rating, botId);
        return "add player successfully";
    }

    @Override
    public String removePlayer(Integer userId) {
        System.out.println("remove player" + userId);
        matchingPool.removePlayer(userId);
        return "remove player successfully";
    }
}
