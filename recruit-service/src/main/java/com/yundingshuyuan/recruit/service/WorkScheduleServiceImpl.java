package com.yundingshuyuan.recruit.service;

import com.yundingshuyuan.recruit.api.WorkScheduleService;
import com.yundingshuyuan.recruit.dao.InterviewPositionMapper;
import com.yundingshuyuan.recruit.dao.InterviewerInfoMapper;
import com.yundingshuyuan.recruit.dao.WorkingScheduleMapper;
import com.yundingshuyuan.recruit.domain.InterviewPosition;
import com.yundingshuyuan.recruit.domain.WorkingSchedule;
import com.yundingshuyuan.recruit.domain.vo.InterviewPositionVo;
import com.yundingshuyuan.recruit.domain.vo.ScheduleVo;
import com.yundingshuyuan.recruit.domain.vo.WorkingScheduleVo;
import io.github.linpeilie.Converter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author cr
 * @Date 2023/7/28 17:28
 */
@Service
@Slf4j
public class WorkScheduleServiceImpl implements WorkScheduleService {

    @Autowired
    InterviewPositionMapper interviewPositionMapper;
    @Autowired
    InterviewerInfoMapper interviewerInfoMapper;
    @Autowired
    WorkingScheduleMapper workingScheduleMapper;
    @Autowired
    Converter converter;


    /**
     * 今天
     */
    @Override
    public LocalDate TODAY() {
        return LocalDate.now();
    }

    /**
     * 明天
     */
    @Override
    public LocalDate TOMORROW() {
        return LocalDate.now().plusDays(1);
    }

    /**
     * 现在
     */
    @Override
    public LocalDateTime NOW() {
        return LocalDateTime.now();
    }

    /**
     * 展示指定日期的面试安排
     *
     * @param date
     * @return
     */
    @Override
    public List<WorkingScheduleVo> showCertainDayAssign(LocalDate date) {
        return workingScheduleMapper.selectCertainDayAssign(date);
    }

    /**
     * 判断指定日期有无面试安排
     * @param date
     * @return
     */
    private boolean checkIsExistAssign(LocalDate date) {
        Integer count = workingScheduleMapper.countCertainDayAssign(date);
        return count>0;
    }

    /**
     * 给指定日期排班
     *
     * @param date
     */
    @Override
    public boolean assignCertainDay(LocalDate date) {
        List<InterviewPositionVo> interviewPositionVos = interviewPositionMapper.certainDayPositions(date);
        int size = interviewPositionVos.size() / 2;
        for (int i = 0; i < size; i++) {
            int groupId = interviewerInfoMapper.minCount();     //排班次数最少的group_id
            interviewerInfoMapper.incrCount(groupId);           //更新排班表
            interviewPositionMapper.assign(groupId);    //更新interviewPosition表的group_id字段
        }
        //从interview_position表中检测出地点和group_id一样，并且开始时间相差一小时的数据
        //按照group_id排序
        List<ScheduleVo> scheduleVos = interviewPositionMapper.selectAssign();
        List<WorkingSchedule> workingSchedules = new ArrayList<>();
        for (ScheduleVo e : scheduleVos
        ) {
            WorkingSchedule workingSchedule = new WorkingSchedule();
            workingSchedule.setGroupId(e.getGroupId());
            workingSchedule.setStartTime(e.getStartTime());
            workingSchedule.setEndTime(e.getEndTime());
            workingSchedule.setInterviewPosition(e.getLocation());
            workingSchedules.add(workingSchedule);
        }
        //批量插入
        return workingScheduleMapper.insertBatch(workingSchedules, workingSchedules.size());

    }


    /**
     * 今日排班
     *
     * @return
     */
    @Override
    public boolean assignToday() {
        boolean result = false;
        return checkIsExistAssign(this.TODAY()) ? result : assignCertainDay(this.TODAY());
    }


    /**
     * 明日排班
     * @return 面试安排表
     */
    @Override
    public boolean assignTomorrow() {
        if (checkIsExistAssign(this.TOMORROW())){
            return false;
        }
        this.assignCertainDay(this.TOMORROW());
        return true;
    }

    /**
     * 当日临时加面试组
     *
     * @param interviewPositionVo
     */
    @Override
    public Boolean tempAddInterview(InterviewPositionVo interviewPositionVo) {
        //开放的面试地点不能被占用，在interview_position中不能有新加的面试点的数据
        validateExist(interviewPositionVo);
        //确认该面试地点没被占用后，将InterviewPositionVo转换为InterviewPosition
        InterviewPosition convert = converter.convert(interviewPositionVo, InterviewPosition.class);
        //直接分配面试官
        int groupId = interviewerInfoMapper.minCount();//寻找被分配的次数最少的面试组
        interviewerInfoMapper.incrCount(groupId);
        convert.setGroupId(groupId);
        //开放面试地点
        int i = interviewPositionMapper.insert(convert);//在interview_position中插入该面试地点
        return i>0;
    }

    /**
     * 删除明日排班信息
     */
    @Override
    public String deleteTomorrowSchedule() {
        List<InterviewPositionVo> interviewPositionVos = interviewPositionMapper.certainDayPositions(TOMORROW());
        List<WorkingScheduleVo> workingScheduleVos = workingScheduleMapper.selectCertainDayAssign(TOMORROW());
        if (interviewPositionVos == null){
            return "请先开放明日面试点";
        }
        if (workingScheduleVos == null){
            return "暂无明日排班信息";
        }
        for (InterviewPositionVo e : interviewPositionVos
             ) {
            interviewerInfoMapper.decrCount(e.getGroupId());
        }
        interviewPositionMapper.updateNull(TOMORROW());
        workingScheduleMapper.deleteCertainDayWorkingSchedule(TOMORROW());
        return "删除成功";
    }

    /**
     * 删除今日排班信息
     *
     * @return
     */
    @Override
    public boolean deleteTodaySchedule() {
        List<InterviewPositionVo> list = interviewPositionMapper.certainDayPositions(TODAY());
        List<WorkingScheduleVo> workingScheduleVos = workingScheduleMapper.selectCertainTimeAssign(NOW());
        if (list == null){
            return false;
        }
        if (workingScheduleVos == null){
            return false;
        }
        for (WorkingScheduleVo e : workingScheduleVos
             ) {
             interviewerInfoMapper.decrCount(e.getGroupId());
        }
        workingScheduleMapper.deleteCertainTimeWorkingSchedule(NOW());
        return true;
    }


    @Override
    public boolean changeSchedule(List<WorkingSchedule> workingSchedules) {
        validateCount(workingSchedules);
        validateOutTime(workingSchedules);
        validateSameDay(workingSchedules);

        Integer groupId1 = workingSchedules.get(0).getGroupId();
        workingSchedules.get(0).setGroupId(workingSchedules.get(1).getGroupId());
        workingSchedules.get(1).setGroupId(groupId1);
        Integer  option1 = workingScheduleMapper.updateWorkScheduleGroupId(workingSchedules.get(0));
        Integer option2 = workingScheduleMapper.updateWorkScheduleGroupId(workingSchedules.get(1));
        return option1 > 0 && option2 > 0;
    }

    public void validateExist(InterviewPositionVo interviewPositionVo) throws InvalidParameterException{
        if (interviewPositionMapper.SelectIsExist(interviewPositionVo) > 0){
            throw new InvalidParameterException("该地点在该时段已经开放过了，请检查您的请求参数");
        }
    }

    public void validateCount(List<WorkingSchedule> w){
        if (w.size() != 2){
            throw new InvalidParameterException("系统只能实现两条数据互相交换，请传入两条数据");
        }
    }

    public void validateSameDay(List<WorkingSchedule> workingSchedules){
        boolean equals = workingSchedules.get(0).getStartTime().toLocalDate().equals(workingSchedules.get(1).getStartTime().toLocalDate());
        if (!equals){
            throw new InvalidParameterException("请选择日期相同的两条记录进行交换");
        }
    }
    public void validateOutTime(List<WorkingSchedule> workingSchedules){

        for (WorkingSchedule e : workingSchedules) {
            if (e.getStartTime().isBefore(NOW()) || e.getEndTime().isBefore(NOW())){
                throw new InvalidParameterException("面试时间已经超过当前服务器时间，无法调换班次");
            }
        }
    }
}
