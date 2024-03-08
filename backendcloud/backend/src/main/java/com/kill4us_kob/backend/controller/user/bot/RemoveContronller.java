package com.kill4us_kob.backend.controller.user.bot;

import com.kill4us_kob.backend.service.user.bot.RemoveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class RemoveContronller {
    @Autowired
    private RemoveService removeService;

    @PostMapping("/user/bots/remove/")
    public Map<String, String> remove(@RequestParam Map<String, String> data) {
        return removeService.remove(data);
    }
}
