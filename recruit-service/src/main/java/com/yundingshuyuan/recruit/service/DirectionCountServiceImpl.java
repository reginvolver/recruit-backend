package com.yundingshuyuan.recruit.service;

import com.yundingshuyuan.recruit.api.DirectionCountService;
import com.yundingshuyuan.recruit.dao.ApplicationPhotoMapper;
import com.yundingshuyuan.recruit.dao.UserInfoMapper;
import com.yundingshuyuan.recruit.domain.vo.DirectionCountVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DirectionCountServiceImpl implements DirectionCountService {
    @Autowired
    private UserInfoMapper userInfoMapper;
    @Autowired
    private ApplicationPhotoMapper applicationPhotoMapper;

    @Override
    public List<DirectionCountVo> getDirectionCounts() {
        return userInfoMapper.countByDirection();
    }

    @Override
    public List<Map<LocalDate, Integer>> getDateCounts() {
        return userInfoMapper.countByDate();
    }
    @Override
    public Map<String, Object> getTotalCounts() {
        int userInfoCount = userInfoMapper.countRegisterInfo();
        int applicationPhotoCount = applicationPhotoMapper.countApplicationPhoto();
        Map<String, Object> counts = new HashMap<>();
        counts.put("userInfoCount", userInfoCount);
        counts.put("applicationPhotoCount", applicationPhotoCount);
        return counts;
    }
}