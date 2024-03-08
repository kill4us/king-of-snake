package com.kill4us_kob.matchingsystem.service.impl;

import com.kill4us_kob.matchingsystem.service.MatchingService;
import org.springframework.stereotype.Service;

@Service
public class MatchingServiceImpl implements MatchingService {
    @Override
    public String addPlayer(Integer userId, Integer rating) {
        System.out.println("add player" + userId + " " + rating);
        return "add player successfully";
    }

    @Override
    public String removePlayer(Integer userId) {
        System.out.println("remove player" + userId);
        return "remove player successfully";
    }
}
