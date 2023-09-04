package com.yundingshuyuan.recruit.domain.vo;

import com.yundingshuyuan.recruit.domain.po.ReservationPo;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Author cr
 * @Date 2023/9/3 19:00
 */
@Data
@AllArgsConstructor
@Builder
@AutoMapper(target = ReservationPo.class)
public class ReserveInterviewTimeVo {
    /**
     * 预约面试时间
     */
    private LocalDateTime interviewTime;

    /**
     * 预约面试的用户id
     */
    private Integer userId;
}
