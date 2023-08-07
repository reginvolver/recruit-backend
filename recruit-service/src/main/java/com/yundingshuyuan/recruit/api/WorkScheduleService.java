package com.yundingshuyuan.recruit.api;

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
     * 判断指定日期有无面试安排
     * @param date
     * @return
     */
    public Boolean checkIsExistAssign(LocalDate date);

    /**
     * 给指定日期排班
     * @param date
     */
    public void assignCertainDay(LocalDate date);


    /**
     * Boolean类型日志输出
     * @param b
     */
    public void logBoolean(boolean b);


    /**
     * Integer类型日志输出
     * @param i
     */
    public void logInteger(int i);



    /**
     * 今日排班
     * @return
     */
    public String assignToday();


    /**
     * 第二天面试安排
     * @return 面试安排表
     */
    public String assignTomorrow();


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
    public String deleteTodaySchedule();


    /**
     * 交换排班
     */
    public String changeSchedule(List<InterviewPositionVo> interviewPositionVos);
}
