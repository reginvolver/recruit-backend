package com.yundingshuyuan.recruit.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yundingshuyuan.recruit.domain.po.LectureCheckInPo;

/**
 * 二维码签到 mapper
 *
 * @author wys
 */
public interface QrCodeCheckInMapper extends BaseMapper {

    LectureCheckInPo selectTicketByOpenId(String openId);

    int updateIsLectureByUserId(long userId);

    long selectTicketByUserAndLectureId(long userId, long lectureId);

    int deleteTicketByUserAndLectureId(long userId, long lectureId);

    boolean selectIfLecturedByUserId(long userId);


}
