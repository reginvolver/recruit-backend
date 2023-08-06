package com.yundingshuyuan.recruit.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Description:
 * Author: NSC
 * Created: 2023/7/31 19:15
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InterviewPositionVo {

    /**
     * 面试场次id
     */
    private Integer id;

    /**
     * 面试小组id
     */
    private Integer groupId;

    /**
     * 面试地点
     */
    private String location;

    /**
     * 开始时间
     */
    private LocalDateTime startTime;

    /**
     * 结束时间
     */
    private LocalDateTime endTime;

    /**
     * 该地点一小时内可容纳人数
     */
    private Integer capacity;

    /**
     * 已容纳的人数
     */
    private Integer contained;
}
