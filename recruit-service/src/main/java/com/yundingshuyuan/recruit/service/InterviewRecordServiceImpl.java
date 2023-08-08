package com.yundingshuyuan.recruit.service;

import com.yundingshuyuan.recruit.domain.po.InterviewRecordPo;
import com.yundingshuyuan.recruit.dao.InterviewRecordMapper;
import com.yundingshuyuan.recruit.api.InterviewRecordService;
import org.springframework.stereotype.Service;


/**
 面试结果查询
 */
@Service
public class InterviewRecordServiceImpl implements InterviewRecordService {

    private final InterviewRecordMapper interviewRecordMapper;

    public InterviewRecordServiceImpl(InterviewRecordMapper interviewRecordMapper) {
        this.interviewRecordMapper = interviewRecordMapper;
    }

    @Override
    public boolean isInterviewPassed(String cloudId) {
        // 根据 cloudId 获取对应的 userId
        Integer userId = interviewRecordMapper.getUserIdByCloudId(cloudId);
        if (userId == null) {
            throw new RuntimeException("Interview not found");
        }
        // 检查是否有面试记录
        InterviewRecordPo interviewRecord = interviewRecordMapper.getByUserId(userId);
        if (interviewRecord == null) {
            throw new RuntimeException("Interview not found");
        }
        //返回面试结果
        return interviewRecord.getIsPassed();
    }

}