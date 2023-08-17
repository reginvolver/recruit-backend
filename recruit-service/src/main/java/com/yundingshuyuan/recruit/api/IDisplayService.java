package com.yundingshuyuan.recruit.api;

import com.yundingshuyuan.recruit.domain.Academy;
import com.yundingshuyuan.recruit.domain.vo.NewsVo;
import com.yundingshuyuan.vo.BasicResultVO;

import java.util.ArrayList;
import java.util.List;

public interface IDisplayService {

    /**
     * 展示新闻
     *
     * @return
     */
    public BasicResultVO<ArrayList<NewsVo>> showNews();

    /**
     * 书院内容展示
     *
     * @return
     */
    public BasicResultVO<List<Academy>> showAcademy();
}
