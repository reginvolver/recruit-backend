package com.yundingshuyuan.recruit.dao;


import com.yundingshuyuan.recruit.domain.LectureTicket;
import com.yundingshuyuan.recruit.domain.vo.LectureTicketVo;

/**
 * <p>
 * 抢票记录表 Mapper 接口
 * </p>
 *
 * @author cr
 * @since 2023-08-03
 */
public interface LectureTicketMapper extends BaseMapperPlus<LectureTicket, LectureTicketVo> {

    Integer checkCount(int userId,int lecture_id);

    LectureTicketVo selectByUserId(int userId,int lectureId);
}
