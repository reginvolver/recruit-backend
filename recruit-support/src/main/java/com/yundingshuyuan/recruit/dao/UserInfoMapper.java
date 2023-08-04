package com.yundingshuyuan.recruit.dao;

import com.yundingshuyuan.recruit.domain.UserInfo;
import com.yundingshuyuan.recruit.domain.vo.UserInfoVo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserInfoMapper extends BaseMapperPlus<UserInfo, UserInfoVo> {
}
