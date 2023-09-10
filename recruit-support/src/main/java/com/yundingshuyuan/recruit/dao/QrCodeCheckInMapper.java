package com.yundingshuyuan.recruit.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yundingshuyuan.recruit.domain.po.InterviewCheckInPo;
import com.yundingshuyuan.recruit.domain.po.LectureCheckInPo;

import java.time.LocalDateTime;

/**
 * 二维码签到 mapper
 *
 * @author wys
 */
public interface QrCodeCheckInMapper extends BaseMapper {
    /* 宣讲会 */
    LectureCheckInPo selectTicketByOpenId(String openId);

    int updateIsLectureByUserId(long userId);

    long selectTicketByUserAndLectureId(long userId, long lectureId);

    int deleteTicketByUserAndLectureId(long userId, long lectureId);

    boolean selectIfLecturedByUserId(long userId);

    /* 面试 */
    InterviewCheckInPo selectInterviewInfoByOpenId(String openId);

    int updateStatusByUserId(long userId);

    LocalDateTime getLectureGarbTime(long lectureId);

}
