package com.yundingshuyuan.recruit.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @Author cr
 * @Date 2023/7/26 22:14
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class IntervieweeVo {
    /**
     * 面试地点
     */
    private Integer interviewId;
    /**
     * 开始时间
     */
    private LocalDateTime startTime;


}
