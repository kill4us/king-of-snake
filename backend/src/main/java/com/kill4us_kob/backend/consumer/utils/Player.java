package com.kill4us_kob.backend.consumer.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Player {
    private Integer id;  //  玩家id
    private Integer sx;
    private Integer sy;
    private List<Integer> step;  //  存储玩家每一步的方向

}
