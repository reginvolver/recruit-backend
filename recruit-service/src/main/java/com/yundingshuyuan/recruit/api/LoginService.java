package com.yundingshuyuan.recruit.api;

public interface LoginService {
    public String token();
    public void login(String username, String password);
    public Boolean isLogin();

}
