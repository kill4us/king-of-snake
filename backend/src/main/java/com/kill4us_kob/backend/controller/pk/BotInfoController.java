package com.kill4us_kob.backend.controller.pk;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping("/pk/")
public class BotInfoController {
    @RequestMapping("getbotinfo/")
    public List<String> getBotInfo() {
        List<String> list = new LinkedList<>();
        list.add("sword");
        list.add("tiger");
        list.add("apple");
        return list;
    }
}
