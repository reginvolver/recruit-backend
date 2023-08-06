package com.yundingshuyuan.recruit.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * <p>
 * 预约面试表
 * </p>
 *
 * @author NSC
 * @since 2023-08-01
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReservationVo {

    /**
     * 预约记录id
     */
    private Integer id;

    /**
     * 预约面试的用户id
     */
    private Integer userId;

    /**
     * 预约面试时间
     */
    private LocalDateTime interviewTime;

    /**
     * 自动分配面试地点的id
     */
    private Integer interviewId;
}
