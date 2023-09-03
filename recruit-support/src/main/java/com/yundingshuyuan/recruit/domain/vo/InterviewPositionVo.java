package com.yundingshuyuan.recruit.domain.vo;


import com.yundingshuyuan.recruit.domain.InterviewPosition;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @Author cr
 * @Date 2023/7/26 21:37
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@AutoMapper(target = InterviewPosition.class)
public class InterviewPositionVo {
    /**
     * 面试地点id
     */
    private Integer id;

    /**
     * 面试地点
     */
    private String location;

    /**
     * 该地点一小时内可容纳人数
     */
    private Integer capacity;


    /**
     * 开始时间
     */
    private LocalDateTime startTime;

    /**
     * 结束时间
     */
    private LocalDateTime endTime;

    /**
     * 分配的面试官组别
     */
    private Integer groupId;

}
