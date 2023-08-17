package com.yundingshuyuan.recruit.service;

import cn.hutool.core.bean.BeanUtil;
import com.yundingshuyuan.recruit.api.IDisplayService;
import com.yundingshuyuan.recruit.dao.AcademyMapper;
import com.yundingshuyuan.recruit.dao.NewsMapper;
import com.yundingshuyuan.recruit.domain.Academy;
import com.yundingshuyuan.recruit.domain.News;
import com.yundingshuyuan.recruit.domain.vo.NewsVo;

import com.yundingshuyuan.vo.BasicResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DisplayServiceImpl implements IDisplayService {

    @Autowired
    private NewsMapper newsMapper;

    @Autowired
    private AcademyMapper academyMapper;

    /**
     * 展示新闻
     *
     * @return newsVos
     */
    @Override
    public BasicResultVO<ArrayList<NewsVo>> showNews() {
        List<News> list = newsMapper.selectList();
        ArrayList<NewsVo> newsVos = new ArrayList<>();
        for (News news : list) {
            NewsVo newsVo = new NewsVo();
            BeanUtil.copyProperties(news, newsVo, true);
            newsVos.add(newsVo);
        }
        if (newsVos.isEmpty()) {
            return BasicResultVO.fail("暂无新闻");
        }
        return BasicResultVO.success(newsVos);
    }

    /**
     * 书院内容展示
     *
     * @return
     */
    @Override
    public BasicResultVO<List<Academy>> showAcademy() {
        List<Academy> academies = academyMapper.selectList(null);
        if (academies.isEmpty()) {
            return BasicResultVO.fail("暂无书院信息");
        }
        return BasicResultVO.success(academies);
    }


}
