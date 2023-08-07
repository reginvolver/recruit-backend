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

    public final String EXIST_TOMORROW = this.TOMORROW() + "的面试信息已经存在了，如果想要重新分配请先删除原有数据";
    public final String EXIST_TODAY = this.TODAY() + "的面试信息已经存在了，如果想要重新分配请先删除原有数据";
    public final String ASSIGN_SUCCESS_TODAY = this.TODAY() + "面试排班成功";
    public final String ASSIGN_SUCCESS_TOMORROW = this.TOMORROW() + "面试排班成功";
    public final String CHANGE_SUCCESS = "交换成功";

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
        List<WorkingScheduleVo> workingScheduleVos = workingScheduleMapper.selectCertainDayAssign(date);
        return workingScheduleVos;
    }

    /**
     * 判断指定日期有无面试安排
     * @param date
     * @return
     */
    @Override
    public Boolean checkIsExistAssign(LocalDate date) {
        Integer count = workingScheduleMapper.countCertainDayAssign(date);
        if (count > 0){
            return true;
        }
        return false;
    }

    /**
     * 给指定日期排班
     *
     * @param date
     */
    @Override
    public void assignCertainDay(LocalDate date) {
        List<InterviewPositionVo> interviewPositionVos = interviewPositionMapper.certainDayPositions(date);
        int size = interviewPositionVos.size() / 2;
        for (int i = 0; i < size; i++) {
            InterviewPositionVo interviewPositionVo = interviewPositionVos.get(i);
            int groupId = interviewerInfoMapper.minCount();     //排班次数最少的group_id
            interviewerInfoMapper.incrCount(groupId);           //更新排班表
            interviewPositionMapper.assign(groupId);    //更新interviewPosition表的group_id字段
        }
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
        boolean b = workingScheduleMapper.insertBatch(workingSchedules,workingSchedules.size());
        logBoolean(b);
    }

    /**
     * Boolean类型日志输出
     *
     * @param b
     */
    @Override
    public void logBoolean(boolean b) {
        if (b){
            log.info("批量插入成功");
        }else {
            log.error("批量插入失败，请自行排查错因");
        }
    }

    /**
     * Integer类型日志输出
     *
     * @param i
     */
    @Override
    public void logInteger(int i) {
        if(i > 0){
            log.info("更改成功，影响" + i +"行数据");
        }else {
            log.error("更改失败，请自行排查错因");
        }
    }


    /**
     * 今日排班
     *
     * @return
     */
    @Override
    public String assignToday() {
        if (checkIsExistAssign(this.TODAY())){
            return EXIST_TODAY;
        }
        this.assignCertainDay(this.TODAY());
        return ASSIGN_SUCCESS_TOMORROW;
    }



    /**
     * 明日排班
     * @return 面试安排表
     */
    @Override
    public String assignTomorrow() {
        if (checkIsExistAssign(this.TOMORROW())){
            return EXIST_TOMORROW;
        }
        this.assignCertainDay(this.TOMORROW());
        return ASSIGN_SUCCESS_TOMORROW;
    }

    /**
     * 当日临时加面试组
     *
     * @param interviewPositionVo
     */
    @Override
    public Boolean tempAddInterview(InterviewPositionVo interviewPositionVo) {
        validateExist(interviewPositionVo);
        InterviewPosition convert = converter.convert(interviewPositionVo, InterviewPosition.class);
        //直接分配面试官
        int group_id = interviewerInfoMapper.minCount();
        int t = interviewerInfoMapper.incrCount(group_id);
        this.logInteger(t);
        convert.setGroupId(group_id);
        //开放面试地点
        int i = interviewPositionMapper.insert(convert);
        this.logInteger(i);
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
            int i = interviewerInfoMapper.decrCount(e.getGroupId());
            this.logInteger(i);
        }
        int i = interviewPositionMapper.updateNull(TOMORROW());
        this.logInteger(i);
        int t = workingScheduleMapper.deleteCertainDayWorkingSchedule(TOMORROW());
        this.logInteger(t);
        return "删除成功";
    }

    /**
     * 删除今日排班信息
     *
     * @return
     */
    @Override
    public String deleteTodaySchedule() {
        List<InterviewPositionVo> list = interviewPositionMapper.certainDayPositions(TODAY());
        List<WorkingScheduleVo> workingScheduleVos = workingScheduleMapper.selectCertainTimeAssign(NOW());
        if (list == null){
            return "请先开放今日面试地点";
        }
        if (workingScheduleVos == null){
            return "暂无今日排班信息";
        }
        for (WorkingScheduleVo e : workingScheduleVos
             ) {
            int i = interviewerInfoMapper.decrCount(e.getGroupId());
            this.logInteger(i);
        }
        int i = workingScheduleMapper.deleteCertainTimeWorkingSchedule(NOW());
        this.logInteger(i);
        return "删除成功";
    }

    /**
     * 交换排班
     *
     * @param interviewPositionVos
     */
    @Override
    public String changeSchedule(List<InterviewPositionVo> interviewPositionVos) {
        validateCount(interviewPositionVos);
        validateOutTime(interviewPositionVos);
        validateSameDay(interviewPositionVos);
        //交换position表中的group_id
        Integer groupId1 = interviewPositionVos.get(0).getGroupId();    //3
        interviewPositionVos.get(0).setGroupId(interviewPositionVos.get(1).getGroupId());
        interviewPositionVos.get(1).setGroupId(groupId1);
        interviewPositionMapper.updateGroupId(interviewPositionVos.get(0));
        interviewPositionMapper.updateGroupId(interviewPositionVos.get(1));
        //交换workingSchedule表中的group_id
        Integer result1 = workingScheduleMapper.updateWorkScheduleGroupId(interviewPositionVos.get(0));
        logInteger(result1);
        Integer result2 = workingScheduleMapper.updateWorkScheduleGroupId(interviewPositionVos.get(1));
        logInteger(result2);
        interviewPositionVos.get(0).setStartTime(interviewPositionVos.get(0).getStartTime().plusHours(1));
        Integer result3 = workingScheduleMapper.updateWorkScheduleGroupId(interviewPositionVos.get(0));
        logInteger(result3);
        interviewPositionVos.get(1).setStartTime(interviewPositionVos.get(1).getStartTime().plusHours(1));
        Integer result4 = workingScheduleMapper.updateWorkScheduleGroupId(interviewPositionVos.get(1));
        logInteger(result4);
        return "交换成功，请刷新相关页面查看结果";
    }

    public void validateExist(InterviewPositionVo interviewPositionVo){
        if (interviewPositionMapper.SelectIsExist(interviewPositionVo) > 0){
            throw new InvalidParameterException("该地点在该时段已经开放过了，请检查您的请求参数");
        }
    }

    public void validateCount(List<InterviewPositionVo> interviewPositionVos){
        if (interviewPositionVos.size() != 2){
            throw new InvalidParameterException("系统只能实现两条数据互相交换，请传入两条数据");
        }
    }

    public void validateSameDay(List<InterviewPositionVo> interviewPositionVos){
        boolean equals = interviewPositionVos.get(0).getStartTime().toLocalDate().equals(interviewPositionVos.get(1).getStartTime().toLocalDate());
        if (!equals){
            throw new InvalidParameterException("请选择日期相同的两条记录进行交换");
        }
    }
    public void validateOutTime(List<InterviewPositionVo> interviewPositionVos){

        for (InterviewPositionVo e : interviewPositionVos
             ) {
            if (e.getStartTime().isBefore(NOW()) || e.getEndTime().isBefore(NOW())){
                throw new InvalidParameterException("面试时间已经超过当前服务器时间，无法调换班次");
            }
        }
    }
}
