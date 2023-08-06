package com.yundingshuyuan.recruit.dao;


import com.yundingshuyuan.recruit.domain.po.Reservation;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 预约面试表 Mapper 接口
 * </p>
 *
 * @author NSC
 * @since 2023-08-01
 */
public interface ReservationMapper extends BaseMapperPlus<Reservation,Reservation> {

    /**
     * 根据报名时间查询尚未分配的预约记录
     * @param interviewTime 报名时间
     * @return 查询结果
     */
    List<Reservation> selectUnassignedByInterviewTime(LocalDateTime interviewTime);
}
