package com.yundingshuyuan.recruit.api;

import com.yundingshuyuan.recruit.domain.vo.InterviewPositionVo;
import com.yundingshuyuan.recruit.domain.vo.InterviewRecordVo;
import com.yundingshuyuan.recruit.domain.vo.IntervieweeVo;
import com.yundingshuyuan.recruit.domain.vo.UserInfoVo;

import java.util.List;

/**
 * @Author cr
 * @Date 2023/7/26 17:11
 */
public interface InterviewService {

    /**
     * 返回所有时间段的所有面试地点信息
     * @return
     */
    public List<InterviewPositionVo> showAllInterviewLocation();

    /**
     * 当前时间段当前地点的面试人员
     * @param intervieweeVo
     * @return
     */
    public List<UserInfoVo> currentInterviewee(IntervieweeVo intervieweeVo);


    /**
     * 之前是否提交过该用户的面评
     * @param userId
     * @return
     */
    public InterviewRecordVo interviewRecordIsExist(Integer userId);

    /**
     * 展示即将面试用户申请书照片
     * @param userId
     * @return
     */
    public List<String> returnApplicationPhotoUrls(Integer userId);

    /**
     * 提交或更新面试记录信息
     * @param interviewRecordVo
     * @return
     */
    public boolean submitFaceback(InterviewRecordVo interviewRecordVo);
}
