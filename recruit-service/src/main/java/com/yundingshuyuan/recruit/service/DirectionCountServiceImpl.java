package com.yundingshuyuan.recruit.service;


import com.yundingshuyuan.recruit.dao.ApplicationPhotoMapper;
import com.yundingshuyuan.recruit.dao.RegisterInfoMapper ;
import com.yundingshuyuan.recruit.domain.vo.DirectionCountVo;
import com.yundingshuyuan.recruit.api.DirectionCountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 报名信息统计
 */
@Service
public class DirectionCountServiceImpl implements DirectionCountService {
    @Autowired
    private RegisterInfoMapper registerInfoMapper;
    @Autowired
    private ApplicationPhotoMapper applicationPhotoMapper;

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
    public List<Map<LocalDate, Integer>> getDateCounts() {
        return registerInfoMapper.countByDate();
    }
    /**
     *统计总的报名人数,提交申请书的人数
     * @return
     */
    @Override
    public Map<String, Object> getTotalCounts() {
        int registerInfoCount = registerInfoMapper.countRegisterInfo();
        int applicationPhotoCount = applicationPhotoMapper.countApplicationPhoto();
        Map<String, Object> counts = new HashMap<>();
        counts.put("registerInfoCount", registerInfoCount);
        counts.put("applicationPhotoCount", applicationPhotoCount);
        return counts;
    }
}