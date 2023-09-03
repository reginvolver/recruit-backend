package com.yundingshuyuan.recruit.domain.vo;



import com.yundingshuyuan.recruit.domain.WorkingSchedule;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @Author cr
 * @Date 2023/7/28 18:13
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@AutoMapper(target = WorkingSchedule.class)
public class WorkingScheduleVo {
    /**
     * 主键id
     */
    private Integer id;

    /**
     * 面试官所在组号
     */
    private Integer groupId;

    /**
     * 开始时间
     */
    private LocalDateTime startTime;

    /**
     * 结束时间
     */
    private LocalDateTime endTime;

    /**
     * 面试地点
     */
    private String interviewPosition;

}
