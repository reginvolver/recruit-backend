package com.yundingshuyuan.recruit.service;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yundingshuyuan.recruit.api.ICollegeService;
import com.yundingshuyuan.recruit.dao.AcademyMapper;
import com.yundingshuyuan.recruit.dao.NewsMapper;
import com.yundingshuyuan.recruit.dao.UserInfoMapper;
import com.yundingshuyuan.recruit.domain.Academy;
import com.yundingshuyuan.recruit.domain.News;
import com.yundingshuyuan.recruit.domain.UserInfo;
import com.yundingshuyuan.recruit.domain.vo.NewsVo;
import com.yundingshuyuan.vo.BasicResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class CollegeServiceImpl implements ICollegeService {
    @Autowired
    private NewsMapper newsMapper;

    @Autowired
    private AcademyMapper academyMapper;

    @Autowired
    private UserInfoMapper userInfoMapper;

    /**
     * 展示新闻
     *
     * @return newsVos
     */
    @Override
    public BasicResultVO<ArrayList<NewsVo>> showNews(Integer academyId) {
        QueryWrapper<News> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("academy_id", academyId);
        List<News> list = newsMapper.selectList(queryWrapper);

        ArrayList<NewsVo> newsVos = new ArrayList<>();
        for (News news : list) {
            NewsVo newsVo = new NewsVo();
            BeanUtil.copyProperties(news,newsVo,true);
            newsVos.add(newsVo);
        }
        if (newsVos.isEmpty()) {
            return BasicResultVO.fail("暂无新闻");
        }
        return BasicResultVO.success(newsVos);
    }



    /**
     * 选择书院
     *
     * @param academyId
     * @return
     */
    @Override
    public BasicResultVO selectAcademy(Integer academyId) {

        UserInfo userInfo = new UserInfo();

        userInfo.setAcademyId(academyId);

        int insert = userInfoMapper.insert(userInfo);

        if (insert > 0 ){
            return BasicResultVO.success();
        }

        return BasicResultVO.fail();

    }


    /**
     * 书院内容展示
     *
     * @return
     */
    @Override
    public BasicResultVO<Academy> showAcademy(Integer id) {
        QueryWrapper<Academy> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id);
        Academy one = academyMapper.selectOne(queryWrapper);
        if (Objects.isNull(one)){
            return BasicResultVO.fail("暂无书院信息");
        }

        return BasicResultVO.success(one);
    }

}
