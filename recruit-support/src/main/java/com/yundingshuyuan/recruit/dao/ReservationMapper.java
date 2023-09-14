package com.yundingshuyuan.recruit.dao;

import com.yundingshuyuan.recruit.domain.po.ReservationPo;
import com.yundingshuyuan.recruit.domain.vo.ReservationVo;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDateTime;
import java.util.List;

/**
 添加面试预约信息
 */
@Mapper
public interface ReservationMapper extends BaseMapperPlus<ReservationPo, ReservationVo> {

    /**
     * 根据报名时间查询尚未分配的预约记录
     * @param interviewTime 报名时间
     * @return 查询结果
     */
    List<ReservationPo> selectUnassignedByInterviewTime(LocalDateTime interviewTime);

    boolean hasReservation(Integer userId);
}