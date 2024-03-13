package com.kill4us_kob.backend.service.impl.record;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kill4us_kob.backend.mapper.RecordMapper;
import com.kill4us_kob.backend.mapper.UserMapper;
import com.kill4us_kob.backend.pojo.User;
import com.kill4us_kob.backend.pojo.record;
import com.kill4us_kob.backend.service.record.GetRecordListService;
import com.sun.prism.impl.Disposer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.awt.image.ImageWatched;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Service
public class GetRecordListServiceImpl implements GetRecordListService {

    @Autowired
    private RecordMapper recordMapper;
    @Autowired
    private UserMapper userMapper;

    @Override
    public JSONObject getList(Integer page) {
        IPage<record> recordIPage = new Page<>(page, 10);
        QueryWrapper<record> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");  //  用id降序排序，查最新的对局id
        List<record> records = recordMapper.selectPage(recordIPage, queryWrapper).getRecords();
        JSONObject resp = new JSONObject();
        List<JSONObject> items = new LinkedList<>();
        for (record record:records) {
            User userA = userMapper.selectById(record.getAId());
            User userB = userMapper.selectById(record.getBId());
            JSONObject item = new JSONObject();
            item.put("a_photo", userA.getPhoto());
            item.put("a_username", userA.getUsername());
            item.put("b_photo", userB.getPhoto());
            item.put("b_username", userB.getUsername());
            String result = "平局";
            if ("A".equals(record.getLoser())) {
                result = "B胜";
            } else if ("B".equals(record.getLoser())) {
                result = "A胜";
            }
            item.put("result", result);
            item.put("record", record);
            items.add(item);
        }
        resp.put("records", items);
        resp.put("records_counts", recordMapper.selectCount(null));
        return resp;

    }
}
