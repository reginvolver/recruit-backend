package com.yundingshuyuan.recruit.domain;

import lombok.Data;

/**
 *方向统计结果封装
 */
@Data
public class DirectionCountVO {
    private String direction;
    private Integer total;
    private Integer today;
    private Integer maleCount;
    private Integer femaleCount;

    public DirectionCountVO(String direction, Integer total, Integer today, Integer maleCount, Integer femaleCount) {
        this.direction = direction;
        this.total = total;
        this.today = today;
        this.maleCount = maleCount;
        this.femaleCount = femaleCount;
    }
}
