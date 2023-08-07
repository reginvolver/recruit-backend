package com.yundingshuyuan.recruit.service;

import cn.hutool.core.util.ObjectUtil;
import com.yundingshuyuan.recruit.api.InterviewService;
import com.yundingshuyuan.recruit.dao.*;
import com.yundingshuyuan.recruit.domain.InterviewPosition;
import com.yundingshuyuan.recruit.domain.InterviewRecord;
import com.yundingshuyuan.recruit.domain.Reservation;
import com.yundingshuyuan.recruit.domain.vo.*;
import io.github.linpeilie.Converter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author cr
 * @Date 2023/7/26 17:42
 */
@Service
public class InterviewServiceImpl implements InterviewService {

    @Autowired
    InterviewPositionMapper interviewPositionMapper;
    @Autowired
    ReservationMapper reservationMapper;
    @Autowired
    UserInfoMapper userInfoMapper;
    @Autowired
    InterviewRecordMapper interviewRecordMapper;
    @Autowired
    ApplicationPhotoMapper applicationPhotoMapper;
    @Autowired
    Converter converter;

    /**
     * 返回所有时间段的所有面试地点信息
     *
     * @return
     */
    @Override
    public List<InterviewPositionVo> showAllInterviewLocation() {
        List<InterviewPosition> interviewPositions = interviewPositionMapper.selectList();
        List<InterviewPositionVo> list = new ArrayList<>();
        for (InterviewPosition e: interviewPositions
             ) {
            InterviewPositionVo interviewPositionVo = new InterviewPositionVo();
            interviewPositionVo.setId(e.getId());
            interviewPositionVo.setLocation(e.getLocation());
            interviewPositionVo.setStartTime(e.getStartTime());
            interviewPositionVo.setEndTime(e.getEndTime());
            list.add(interviewPositionVo);
        }
        return list;
    }

    /**
     * 当前时间段当前地点的面试人员
     *
     * @param intervieweeVo
     * @return
     */
    @Override
    public List<UserInfoVo> currentInterviewee(IntervieweeVo intervieweeVo) {
        Map<String,Object> map = new HashMap<>();
        map.put("interview_time",intervieweeVo.getStartTime());
        map.put("interview_id",intervieweeVo.getInterviewId());
        List<Reservation> reservations = reservationMapper.selectByMap(map);
        List<UserInfoVo> userInfos = new ArrayList<>();
        for (Reservation e : reservations
             ) {
            Integer userId = e.getUserId();
            UserInfoVo userInfo = userInfoMapper.selectVoById(userId);
            userInfos.add(userInfo);
        }

        return userInfos;
    }

    /**
     * 之前是否提交过该用户的面评，并返回申请书照片
     *
     * @param userId
     * @return
     */
    @Override
    public InterviewRecordVo interviewRecordIsExist(Integer userId) {
        InterviewRecordVo interviewRecordVo = interviewRecordMapper.isExistByUserId(userId);
        if (interviewRecordVo == null){
            return null;
        }
        return interviewRecordVo;
    }

    /**
     * 展示用户申请书
     *
     * @param userId
     * @return
     */
    @Override
    public List<String> returnApplicationPhotoUrls(Integer userId) {
        Map<String,Object> map = new HashMap<>();
        map.put("user_id",userId);
        List<ApplicationPhotoVo> applicationPhotoVos = applicationPhotoMapper.selectVoByMap(map);
        List<String> urls = new ArrayList<>();
        for (ApplicationPhotoVo e : applicationPhotoVos
        ) {
            String url = e.getUrl();
            urls.add(url);
        }
        return urls;
    }

    /**
     * 提交或更新面试记录信息
     *
     * @param interviewRecordVo
     * @return
     */
    @Override
    public boolean submitFaceback(InterviewRecordVo interviewRecordVo) {

        if (ObjectUtil.isEmpty(interviewRecordMapper.isExistByUserId(interviewRecordVo.getUserId()))){
            InterviewRecord convert = converter.convert(interviewRecordVo, InterviewRecord.class);
            int insert = interviewRecordMapper.insert(convert);
            return insert>0;
        }
        int i = interviewRecordMapper.updateRecord(interviewRecordVo);
        return i>0;
    }
    


}
