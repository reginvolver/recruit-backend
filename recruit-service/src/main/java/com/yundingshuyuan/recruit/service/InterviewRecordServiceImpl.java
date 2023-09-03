package com.yundingshuyuan.recruit.service;

import com.yundingshuyuan.recruit.api.InterviewRecordService;
import com.yundingshuyuan.recruit.dao.UserInfoMapper;
import com.yundingshuyuan.recruit.domain.UserInfo;
import org.springframework.stereotype.Service;

/**
 面试结果查询
 */
@Service
public class InterviewRecordServiceImpl implements InterviewRecordService {

    private final UserInfoMapper userInfoMapper;

    public InterviewRecordServiceImpl(UserInfoMapper userInfoMapper) {
        this.userInfoMapper = userInfoMapper;
    }

    @Override
    public Integer isInterviewPassed(Integer userId) {
        if (userId == null) {
            throw new RuntimeException("User ID is null");
        }

        UserInfo userInfo = userInfoMapper.getByUserId(userId);
        return userInfo.getIsPassed();

    }
}