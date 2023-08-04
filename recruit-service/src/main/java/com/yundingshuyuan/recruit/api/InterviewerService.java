package com.yundingshuyuan.recruit.api;

import java.io.FileNotFoundException;

public interface InterviewerService {
    /**
     * Description 生成面试系统账号密码
     *
     * @param id   id
     * @param name 名字
     * @return {@link Integer }
     * @author 李朋逊
     * @date 2023/08/02
     */
    public Integer generateCode(Integer id, String name);

    /**
     * Description 生成面试系统的excel表格
     *
     * @author 李朋逊
     * @date 2023/08/02
     */
    public void excel() throws FileNotFoundException;
}
