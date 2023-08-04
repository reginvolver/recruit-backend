package com.yundingshuyuan.recruit.dao;

import com.yundingshuyuan.recruit.domain.Academy;
import com.yundingshuyuan.recruit.domain.vo.AcademyVo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AcademyMapper extends BaseMapperPlus<Academy, AcademyVo> {
}
