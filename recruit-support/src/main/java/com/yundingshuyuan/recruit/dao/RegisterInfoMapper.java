package com.yundingshuyuan.recruit.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yundingshuyuan.recruit.domain.DirectionCountVO;
import com.yundingshuyuan.recruit.domain.RegisterInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface RegisterInfoMapper extends BaseMapper<RegisterInfo> {
    @Select("SELECT register_info.direction,\n" +
            "       COUNT(*) AS total,\n" +
            "       SUM(CASE WHEN DATE(register_info.create_time) = CURDATE() THEN 1 ELSE 0 END) AS today,\n" +
            "       SUM(CASE WHEN user_info.gender = 1 THEN 1 ELSE 0 END) AS maleCount,\n" +
            "       SUM(CASE WHEN user_info.gender = 0 THEN 1 ELSE 0 END) AS femaleCount\n" +
            "FROM register_info\n" +
            "JOIN user_info ON register_info.user_id = user_info.id\n" +
            "GROUP BY register_info.direction")
    List<DirectionCountVO> countByDirection();

    @Select("SELECT COUNT(*) FROM register_info")
    int countRegisterInfo();

}