package com.yundingshuyuan.recruit.dao;

import com.yundingshuyuan.recruit.domain.po.InterviewPosition;
import com.yundingshuyuan.recruit.domain.vo.InterviewPositionVo;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 面试场次表 Mapper 接口
 * </p>
 *
 * @author NSC
 * @since 2023-07-31
 */
public interface InterviewPositionMapper extends BaseMapperPlus<InterviewPosition, InterviewPositionVo> {
    /**
     * 查看一个场次是否已存在
     * @param interviewPosition 要查询的场次
     * @return 是否存在，1存在，0不存在
     */
    int isExisted(InterviewPosition interviewPosition);

    /**
     * 查询已开放的尚未占满的时间段
     * @return
     */
    List<LocalDateTime> selectOpenedTimeListNotFull();

    /**
     * 查询某个时间段内还可以容纳人数的地点有哪些
     * @param startTime 时间段开始时间
     * @return 还可以容纳人数的地点列表
     */
    List<InterviewPosition> searchListNotFullByStartTime(LocalDateTime startTime);

}
