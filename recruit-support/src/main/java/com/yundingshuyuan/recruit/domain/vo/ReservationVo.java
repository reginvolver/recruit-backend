package com.yundingshuyuan.recruit.domain.vo;

import com.yundingshuyuan.recruit.domain.Reservation;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @Author cr
 * @Date 2023/7/26 23:11
 */
@Data
@AllArgsConstructor
@Builder
@AutoMapper(target = Reservation.class)
public class ReservationVo {
    /**
     * 预约记录id
     */
    private Integer id;

    /**
     * 预约面试时间
     */
    private LocalDateTime interviewTime;

    /**
     * 预约面试的用户id
     */
    private Integer userId;

    /**
     * 自动分配面试点id
     */
    private Integer interviewId;

}
