package com.yundingshuyuan.recruit.api;

import java.io.FileNotFoundException;

public interface InterviewerService {
    public Integer generateCode(Integer id, String name);

    public void excel() throws FileNotFoundException;
}
