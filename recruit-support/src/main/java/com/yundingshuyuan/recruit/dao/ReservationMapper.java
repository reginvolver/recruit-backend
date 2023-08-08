package com.yundingshuyuan.recruit.dao;

import com.yundingshuyuan.recruit.domain.po.ReservationPo;
import com.yundingshuyuan.recruit.domain.vo.ReservationVo;
import org.apache.ibatis.annotations.Mapper;

/**
 添加面试预约信息
 */
@Mapper
public interface ReservationMapper extends BaseMapperPlus<ReservationPo, ReservationVo> {

    Integer getUserIdByCloudId(String cloudId);

    boolean hasReservation(Integer userId);
}