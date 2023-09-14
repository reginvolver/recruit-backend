package com.yundingshuyuan.recruit.api;

import com.yundingshuyuan.recruit.domain.vo.UserInfoVo;

public interface UserInfoService {
    /**
     * Description 显示用户信息
     *
     * @param id id
     * @return {@link UserInfoVo }
     * @author 李朋逊
     * @date 2023/08/01
     */
    public UserInfoVo showUserInfo(String cloudId);


    /**
     * Description 更新用户信息
     *
     * @param userInfoVO 用户信息
     * @param isAdmin
     * @return boolean
     * @author 李朋逊
     * @date 2023/08/02
     */
    public boolean updateUserInfo(UserInfoVo userInfoVO, Integer isAdmin);

    /**
     * Description 保存用户信息
     *
     * @param userInfoVO 用户信息
     * @return boolean
     * @author 李朋逊
     * @date 2023/08/02
     */
    public boolean saveUserInfo(UserInfoVo userInfoVO);

}
