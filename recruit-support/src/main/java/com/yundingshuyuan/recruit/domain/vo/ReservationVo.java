package com.yundingshuyuan.recruit.domain.vo;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 用户预约面试表
 */
@Data
public class ReservationVo {
    /**
     * 预约ID
     */
    private int id;

    /**
     * 面试时间
     */
    private LocalDateTime interviewTime;

    /**
     * 用户ID
     */
    private int userId;

    /**
     * 版乐观锁
     */
    private int version;

    /**
     * 是否已删除
     */
    private boolean deleted;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 面试ID
     */
    private Integer interviewId;


    // Getter and Setter methods
}