package com.yundingshuyuan.recruit.service;

import com.yundingshuyuan.vo.BasicResultVO;

public interface IDisplayService {

    /**
     * 展示新闻
     *
     * @return
     */
    public BasicResultVO showNews();

    /**
     * 书院内容展示
     *
     * @return
     */
    public BasicResultVO showAcademy();
}
