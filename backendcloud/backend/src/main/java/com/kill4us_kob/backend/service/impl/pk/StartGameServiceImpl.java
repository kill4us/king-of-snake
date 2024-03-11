package com.kill4us_kob.backend.service.impl.pk;

import com.kill4us_kob.backend.consumer.WebSocketServer;
import com.kill4us_kob.backend.service.pk.StartGameService;
import org.springframework.stereotype.Service;

@Service
public class StartGameServiceImpl implements StartGameService {
    @Override
    public String startGame(Integer aId, Integer bId, Integer a_botId, Integer b_botId) {
        System.out.println("start game!" + aId + " " + bId);
        WebSocketServer.startGame(aId, bId, a_botId, b_botId);
        return "start game success";
    }
}
