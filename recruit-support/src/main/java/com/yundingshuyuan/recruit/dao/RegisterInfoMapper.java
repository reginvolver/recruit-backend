package com.yundingshuyuan.recruit.dao;

import com.yundingshuyuan.recruit.domain.po.RegisterInfoPo;
import com.yundingshuyuan.recruit.domain.vo.DirectionCountVo;
import com.yundingshuyuan.recruit.domain.vo.RegisterInfoVo;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Mapper
public interface RegisterInfoMapper extends BaseMapperPlus<RegisterInfoPo, RegisterInfoVo> {
    List<DirectionCountVo> countByDirection();

    @MapKey("date")
    List<Map<LocalDate, Integer>> countByDate();

}