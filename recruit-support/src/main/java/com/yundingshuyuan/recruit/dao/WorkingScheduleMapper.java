package com.yundingshuyuan.recruit.dao;


import com.yundingshuyuan.recruit.domain.WorkingSchedule;
import com.yundingshuyuan.recruit.domain.vo.InterviewPositionVo;
import com.yundingshuyuan.recruit.domain.vo.WorkingScheduleVo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 排班表 Mapper 接口
 * </p>
 *
 * @author cr
 * @since 2023-07-28
 */
public interface WorkingScheduleMapper extends BaseMapperPlus<WorkingSchedule, WorkingScheduleVo> {

    /**
     * 删除指定日期，且时间在当前服务器时间之后的面试官排班记录
     * @param startTime
     * @return
     */
    int deleteCertainTimeWorkingSchedule(LocalDateTime startTime);

    /**
     * 删除指定日期的面试官排班记录
     * @param startTime
     * @return
     */
    int deleteCertainDayWorkingSchedule(LocalDate startTime);

    /**
     * 查找指定日期的面试安排
     * @param startTime
     * @return
     */
    List<WorkingScheduleVo> selectCertainDayAssign(LocalDate startTime);

    /**
     * 当日，当前时间之后的面试安排
     * @param startTime
     * @return
     */
    List<WorkingScheduleVo> selectCertainTimeAssign(LocalDateTime startTime);

    /**
     * 指定日期的面试安排数量
     * @param startTime
     * @return
     */
    Integer countCertainDayAssign(LocalDate startTime);

    Integer updateWorkScheduleGroupId(InterviewPositionVo interviewPositionVo);


}
