package com.yundingshuyuan.recruit.api;

import com.yundingshuyuan.recruit.domain.vo.UserInfoVO;

public interface UserInfoService {
    /**
     * Description 显示用户信息
     *
     * @param id id
     * @return {@link UserInfoVO }
     * @author 李朋逊
     * @date 2023/08/01
     */
    public UserInfoVO showUserInfo(Integer id);


    /**
     * Description 更新用户信息
     *
     * @param userInfoVO 用户信息
     * @return boolean
     * @author 李朋逊
     * @date 2023/08/02
     */
    public boolean updateUserInfo(UserInfoVO userInfoVO);

    /**
     * Description 保存用户信息
     *
     * @param userInfoVO 用户信息
     * @return boolean
     * @author 李朋逊
     * @date 2023/08/02
     */
    public boolean saveUserInfo(UserInfoVO userInfoVO);

}
