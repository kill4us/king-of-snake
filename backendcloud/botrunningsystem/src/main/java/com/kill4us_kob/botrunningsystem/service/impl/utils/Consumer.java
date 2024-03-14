package com.kill4us_kob.botrunningsystem.service.impl.utils;

import com.kill4us_kob.botrunningsystem.utils.BotInterface;
import org.joor.Reflect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.UUID;
import java.util.function.Supplier;

@Component
public class Consumer extends Thread {

    private Bot bot;

    private final static String receiveBotMoveUrl = "http://127.0.0.1:3000/pk/receive/bot/move/";

    private static RestTemplate restTemplate;

    @Autowired
    public void setRestTemplate(RestTemplate restTemplate) {
        Consumer.restTemplate = restTemplate;
    }

    public void startTimeout(long timeout, Bot bot) {
        this.bot = bot;
        this.start();  //  开启一个新的线程，执行run函数

        try {
            this.join(timeout);  //  最多等待timeout秒，然后继续执行下面的语句
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            this.interrupt();  //  中断
        }
    }

    private String addUid(String code, String uid) {  //  在code中的Bot类名后加上uid
        int k = code.indexOf(" implements java.util.function.Supplier<Integer>");
        return code.substring(0, k) + uid + code.substring(k);  //  先返回code前面的，再返回uid，再返回code后面的
    }

    @Override
    public void run() {
        UUID uuid = UUID.randomUUID();
        String uid = uuid.toString().substring(0, 8);  //  返回前八位随机字符串



        Supplier<Integer> botInterface = Reflect.compile(
                "com.kill4us_kob.botrunningsystem.utils.Bot" + uid,  //  保证每次类名不一样
                addUid(bot.getBotCode(), uid)
        ).create().get();

        File file = new File("input.txt");

        try (PrintWriter fout = new PrintWriter(file)){
            fout.println(bot.getInput());
            fout.flush();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        Integer direction = botInterface.get();

        System.out.println("move-direcion: " + bot.getUserId() + " " + direction);

        MultiValueMap<String, String> data = new LinkedMultiValueMap<>();
        data.add("user_id", bot.getUserId().toString());
        data.add("direction", direction.toString());
        //  将结果返回
        restTemplate.postForObject(receiveBotMoveUrl, data, String.class);
    }
}
