package com.yundingshuyuan.recruit.domain.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 面试记录表
 */
@Data
public class InterviewRecordVo {
    /**
     * 面试记录ID
     */
    private Integer id;

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 面评内容
     */
    private String feedback;

    /**
     * 评分
     */
    private BigDecimal score;

    /**
     * 面试官
     */
    private String interviewer;

    /**
     * 是否通过面试
     */
    private Boolean isPassed;
}
