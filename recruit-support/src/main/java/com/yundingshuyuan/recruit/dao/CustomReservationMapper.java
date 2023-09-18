package com.yundingshuyuan.recruit.dao;

import com.yundingshuyuan.recruit.domain.ReservationEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CustomReservationMapper {
    List<ReservationEntity> getReservationDataForExport();
}