package com.kill4us_kob.matchingsystem.service.impl.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

@Component
public class MatchingPool extends Thread{

    private static List<Player> players = new ArrayList<>();

    private ReentrantLock lock = new ReentrantLock();

    private static RestTemplate restTemplate;

    private static final String startGameUrl = "http://127.0.0.1:3000/pk/start/game/";

    @Autowired
    private void setRestTemplate(RestTemplate restTemplate) {
        MatchingPool.restTemplate = restTemplate;
    }

    public void addPlayer(Integer userId, Integer rating, Integer botId) {
        lock.lock();
        try {
            players.add(new Player(userId, rating, 0, botId));
        } finally {
            lock.unlock();
        }
    }

    public void removePlayer(Integer userId) {
        lock.lock();
        try {
            List<Player> newplayers = new ArrayList<>();
            for (Player player:players) {
                if (!player.getUserId().equals(userId)) {
                    newplayers.add(player);
                }
            }
            players = newplayers;
        } finally {
            lock.unlock();
        }
    }

    private void increaseWaitingTime() {  //  所有玩家的等待时间加1
        for (Player player:players) {
            player.setWaitingTime(player.getWaitingTime() + 1);  //  将等待时间加一
        }
    }

    private boolean checkMatched(Player a, Player b) {  //  判断两名玩家是否匹配
        int ratingDelta = Math.abs(a.getRating() - b.getRating());
        int waitingTime = Math.min(a.getWaitingTime(), b.getWaitingTime());  //  双方等待时间的最小值，必须双方互相满足条件才可匹配
        return ratingDelta <= waitingTime * 10;
    }

    private void sendResult(Player a, Player b) {  //  返回a和b的匹配结果
        MultiValueMap<String, String> data = new LinkedMultiValueMap<>();
        data.add("a_id", a.getUserId().toString());
        data.add("b_id", b.getUserId().toString());
        data.add("a_botId", a.getBotId().toString());
        data.add("b_botId", b.getBotId().toString());
        restTemplate.postForObject(startGameUrl, data, String.class);
    }

    private void matchPlayers() {  //  尝试匹配所有玩家

        boolean[] used = new boolean[players.size()];
        for (int i = 0; i < players.size(); i ++ ) {
            if (used[i]) {
                continue;
            }
            for (int j = i + 1; j < players.size(); j ++ ) {
                if (used[j]) continue;
                Player a = players.get(i);
                Player b = players.get(j);
                if (checkMatched(a, b)) {
                    used[i] = used[j] = true;
                    sendResult(a, b);
                    break;
                }
            }
        }

        List<Player> newPlayers = new ArrayList<>();
        for (int i = 0; i < players.size(); i ++ ) {
            if (!used[i]) {
                newPlayers.add(players.get(i));
            }
        }
        players = newPlayers;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(1000);
                lock.lock();
                try {
                    increaseWaitingTime();
                    matchPlayers();
                } finally {
                    lock.unlock();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                break;
            }


        }
    }
}
