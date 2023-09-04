package com.yundingshuyuan.recruit.dao;

import com.yundingshuyuan.recruit.domain.InterviewPosition;
import com.yundingshuyuan.recruit.domain.vo.InterviewPositionVo;
import com.yundingshuyuan.recruit.domain.vo.ScheduleVo;

import java.time.LocalDate;
import java.util.List;


/**
 * <p>
 * 面试地点表 Mapper 接口
 * </p>
 *
 * @author author
 * @since 2023-07-26
 */

public interface InterviewPositionMapper extends BaseMapperPlus<InterviewPosition, InterviewPositionVo> {




    /**
     * 指定日期的所有面试点
     * @param startDate
     * @return
     */
    List<InterviewPositionVo> certainDayPositions(LocalDate startDate);

    /**
     * 分组操作
     * @param group_id
     * @return
     */
    int assign(int group_id);

    /**
     * 排班前找到即将要排的班
     * @return
     */
    List<ScheduleVo> selectAssign();

    /**
     * 将指定日期的location分配到的面试官group_id变为null
     * @param startDate
     * @return
     */
    int updateNull(LocalDate startDate);

    /**
     * 判断一条面试地点信息是否已经插入过了
     *
     * @param interviewPositionVo
     * @return
     */
    Integer SelectIsExist(InterviewPositionVo interviewPositionVo);

    Integer updateGroupId(InterviewPositionVo interviewPositionVo);
}
