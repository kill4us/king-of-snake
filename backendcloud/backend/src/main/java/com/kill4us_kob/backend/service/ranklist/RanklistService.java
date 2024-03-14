package com.kill4us_kob.backend.service.ranklist;

import com.alibaba.fastjson.JSONObject;

public interface RanklistService {
    JSONObject getRanklist(Integer page);
}
