package com.kill4us_kob.backend.consumer.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Player {
    private Integer id;  //  玩家id
    private Integer sx;
    private Integer sy;
    private List<Integer> step;  //  存储玩家每一步的方向
    private Integer botId;  //  -1表示人工,否则表示代码操作
    private String botCode;

    private boolean check_tail_increasing(int step) {
        if (step <= 10) return true;
        return step % 3 == 1;
    }

    public List<Cell> getCells() {
        List<Cell> res = new ArrayList<>();

        int[] dx = {-1, 0, 1, 0}, dy = {0, 1, 0, -1};
        int x = sx, y = sy;
        int round = 0;
        res.add(new Cell(x, y));
        for (int d : step) {
            x += dx[d];
            y += dy[d];
            res.add(new Cell(x, y));
            if (!check_tail_increasing( ++ round)) {
                res.remove(0);

            }

        }
        return  res;
    }

    public String getStepString() {
        StringBuilder res = new StringBuilder();
        for (int d:step) {
            res.append(d);
        }
        return res.toString();
    }
}
