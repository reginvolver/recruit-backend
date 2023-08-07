package com.yundingshuyuan.recruit.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @Author cr
 * @Date 2023/7/28 23:15
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ScheduleVo {

    /**
     * 主键id
     */
    Integer id;
    /**
     * 面试官group_id
     */
    Integer groupId;
    /**
     * 面试地点
     */
    String location;
    /**
     * 排班开始时间
     */
    LocalDateTime startTime;
    /**
     * 排班结束时间
     */
    LocalDateTime endTime;


}
