package com.yundingshuyuan.recruit.api;

import com.yundingshuyuan.recruit.domain.WorkingSchedule;
import com.yundingshuyuan.recruit.domain.vo.InterviewPositionVo;
import com.yundingshuyuan.recruit.domain.vo.WorkingScheduleVo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @Author cr
 * @Date 2023/7/28 17:27
 */
public interface WorkScheduleService {

    /**
     * 今天
     */
    public LocalDate TODAY();


    /**
     * 明天
     */
    public LocalDate TOMORROW();


    /**
     * 现在
     */
    public LocalDateTime NOW();

    /**
     * 展示指定日期的面试安排
     *
     * @param date
     * @return
     */
    public List<WorkingScheduleVo> showCertainDayAssign(LocalDate date);



    /**
     * 给指定日期排班
     * @param date
     */
    public boolean assignCertainDay(LocalDate date);





    /**
     * 今日排班
     * @return
     */
    public boolean assignToday();


    /**
     * 第二天面试安排
     * @return 面试安排表
     */
    public boolean assignTomorrow();


    /**
     * 当日临时加面试组
     */
    public Boolean tempAddInterview(InterviewPositionVo interviewPositionVo);


    /**
     * 删除明日排班信息
     */
    public String deleteTomorrowSchedule();


    /**
     * 删除今日排班信息
     * @return
     */
    public boolean deleteTodaySchedule();


    /**
     * 交换排班
     *
     * @param workingSchedules
     * @return 是否交换成功
     */
    public boolean changeSchedule(List<WorkingSchedule> workingSchedules);
}
