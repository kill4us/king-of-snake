package com.kill4us_kob.backend.controller.user.bot;

import com.kill4us_kob.backend.pojo.bot;
import com.kill4us_kob.backend.service.user.bot.GetlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GetlistController {
    @Autowired
    private GetlistService getlistService;

    @GetMapping("/api/user/bots/getlist/")
    public List<bot> getlist() {
        return getlistService.getList();
    }
}
