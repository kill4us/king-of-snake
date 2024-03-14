package com.kill4us_kob.backend.controller.ranklist;

import com.alibaba.fastjson.JSONObject;
import com.kill4us_kob.backend.service.ranklist.RanklistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class GetRanklistController {
    @Autowired
    private RanklistService ranklistService;

    @GetMapping("/api/ranklist/getlist/")
    public JSONObject getRanklist(@RequestParam Map<String, String> data) {
        Integer page = Integer.parseInt(data.get("page"));
        return ranklistService.getRanklist(page);
    }
}
