package com.yundingshuyuan.recruit.domain.vo;

import lombok.Data;

/**
 *方向统计结果封装
 */
@Data
public class DirectionCountVo {
    /**
     方向
     */
    private String direction;
    /**
     报名总人数
     */
    private Integer total;
    /**
     今日计数
     */
    private Integer today;
    /**
     男性人数
     */
    private Integer maleCount;
    /**
     女性人数
     */
    private Integer femaleCount;
}
