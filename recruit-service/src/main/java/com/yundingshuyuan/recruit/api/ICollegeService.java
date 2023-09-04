package com.yundingshuyuan.recruit.api;

import com.yundingshuyuan.recruit.domain.Academy;
import com.yundingshuyuan.recruit.domain.vo.NewsVo;
import com.yundingshuyuan.vo.BasicResultVO;

import java.util.ArrayList;

public interface ICollegeService {

    /**
     * 展示新闻
     *
     * @return
     */
    public BasicResultVO<ArrayList<NewsVo>> showNews(Integer academyId);

    /**
     * 书院内容展示
     *
     * @return
     */
    public BasicResultVO<Academy> showAcademy(Integer id);

    /**
     * 选择书院
     * @param academyId
     * @return
     */
    public BasicResultVO selectAcademy(Integer academyId);

}
