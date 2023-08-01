package com.yundingshuyuan.recruit.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yundingshuyuan.recruit.domain.po.LectureCheckInPo;

public interface QrCodeCheckInMapper extends BaseMapper {

    LectureCheckInPo selectTicket(String openId);

    int checkInLectureByUserId(long userId);

    int checkInLectureByOpenId(long openId);
}
