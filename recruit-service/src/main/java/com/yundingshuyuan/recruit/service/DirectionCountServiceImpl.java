package com.yundingshuyuan.recruit.service;


import com.yundingshuyuan.recruit.dao.RegisterInfoMapper ;
import com.yundingshuyuan.recruit.domain.vo.DirectionCountVo;
import com.yundingshuyuan.recruit.api.DirectionCountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 报名信息统计
 */
@Service
public class DirectionCountServiceImpl implements DirectionCountService {
    @Autowired
    private RegisterInfoMapper registerInfoMapper;

    /**
     * 统计各方向报名人数，当日报名人数，男女比例
     * @return
     */
    @Override
    public List<DirectionCountVo> getDirectionCounts() {
        return registerInfoMapper.countByDirection();
    }

    /**
     *统计总的每一天的报名人数
     * @return
     */
    @Override
    public List<Map<LocalDate, Integer>> getTotalCounts() {
        return registerInfoMapper.countByDate();
    }
}