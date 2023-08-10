package com.yundingshuyuan.recruit.api;

public interface LoginService {
    /**
     * Description 获取当前登录id
     *
     * @return {@link Integer }
     * @author 李朋逊
     * @date 2023/08/02
     */
    public Integer getCurrentId();

    /**
     * Description 登录
     *
     * @param username 用户名
     * @param password 密码
     * @author 李朋逊
     * @date 2023/08/02
     */
    public void login(String username, String password);

    public Boolean isLogin();


}
