package com.yundingshuyuan.recruit.dao;


import com.yundingshuyuan.recruit.domain.UserInfo;
import com.yundingshuyuan.recruit.domain.vo.UserInfoVo;

/**
 * <p>
 * 用户信息表 Mapper 接口
 * </p>
 *
 * @author cr
 * @since 2023-07-26
 */
public interface UserInfoMapper extends BaseMapperPlus<UserInfo, UserInfoVo> {

    Integer incrCount(Integer userId);

}
