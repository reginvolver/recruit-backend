package com.yundingshuyuan.recruit.dao;

import com.yundingshuyuan.recruit.domain.UserInfo;
import com.yundingshuyuan.recruit.domain.vo.DirectionCountVo;
import com.yundingshuyuan.recruit.domain.vo.UserInfoVo;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Mapper
public interface UserInfoMapper extends BaseMapperPlus<UserInfo, UserInfoVo> {

    Integer incrCount(Integer userId);

    UserInfo getByUserId(Integer userId);

    List<DirectionCountVo> countByDirection();


    @MapKey("date")
    List<Map<LocalDate, Integer>> countByDate();

    int countRegisterInfo();
}



