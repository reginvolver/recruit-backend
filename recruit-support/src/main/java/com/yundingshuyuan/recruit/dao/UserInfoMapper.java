package com.yundingshuyuan.recruit.dao;

import com.yundingshuyuan.recruit.domain.UserInfo;
<<<<<<< HEAD
import com.yundingshuyuan.recruit.domain.vo.UserInfoVo;

public interface UserInfoMapper extends BaseMapperPlus<UserInfo, UserInfoVo> {
=======
import com.yundingshuyuan.recruit.domain.vo.UserInfoVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserInfoMapper extends BaseMapperPlus<UserInfo, UserInfoVO> {
>>>>>>> a23add2a3ac099993d23262d1f5adefc8a962bfb
}
