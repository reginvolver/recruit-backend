package com.yundingshuyuan.recruit.api;

import com.yundingshuyuan.recruit.domain.InterviewerInfo;

public interface LoginService {
    public Integer getCurrentId();

    public void login(String username, String password);

    public Boolean isLogin();

    public Integer generate(InterviewerInfo info);

}
