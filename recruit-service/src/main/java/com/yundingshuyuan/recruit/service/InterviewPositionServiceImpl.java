package com.yundingshuyuan.recruit.service;


import cn.hutool.core.util.StrUtil;
import com.yundingshuyuan.recruit.api.IInterviewPositionService;
import com.yundingshuyuan.recruit.dao.InterviewPositionMapper;
import com.yundingshuyuan.recruit.dao.ReservationMapper;
import com.yundingshuyuan.recruit.domain.InterviewPosition;
import com.yundingshuyuan.recruit.domain.po.ReservationPo;
import com.yundingshuyuan.recruit.domain.vo.InterviewPositionVo;
import com.yundingshuyuan.recruit.utils.MapstructUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * <p>
 * 面试场次表 服务实现类
 * </p>
 *
 * @author NSC
 * @since 2023-07-31
 */
@Service
public class InterviewPositionServiceImpl implements IInterviewPositionService {

    @Autowired
    InterviewPositionMapper interviewPositionMapper;

    @Autowired
    ReservationMapper reservationMapper;

    /**
     * 获取全部的面试地点
     *
     * @return 返回获取到的面试地点列表
     */
    @Override
    public List<InterviewPositionVo> selectAll() {
        return interviewPositionMapper.selectVoList();
    }

    /**
     * 添加新的面试场次
     *
     * @param interviewPositionVo 新面试场次DTO
     */
    @Override
    public void addInterviewPosition(InterviewPositionVo interviewPositionVo) {

        //参数校验
        LocalDateTime startTime = interviewPositionVo.getStartTime();
        LocalDateTime endTime = interviewPositionVo.getEndTime();

        if (StrUtil.isBlank(interviewPositionVo.getLocation())) {
            throw new IllegalArgumentException("地点格式错误");
        }

        if (startTime == null || endTime == null || !startTime.plusHours(1).isEqual(endTime)){
            throw new IllegalArgumentException("时间格式错误");
        }

        //可容纳人数上限为4
        if (interviewPositionVo.getCapacity() == null) {
            interviewPositionVo.setCapacity(4);
        }

        if (interviewPositionVo.getCapacity() <= 0 || interviewPositionVo.getCapacity() > 4) {
            throw new IllegalArgumentException("可容纳人数错误");
        }

        //已分配人数默认值为0
        interviewPositionVo.setContained(0);

        InterviewPosition interviewPosition = MapstructUtils.convert(interviewPositionVo, InterviewPosition.class);

        int existed = interviewPositionMapper.isExisted(interviewPosition);

        if (existed == 1) {
            throw new IllegalArgumentException("该记录已存在");
        } else {
            int insert = interviewPositionMapper.insert(interviewPosition);
            if (insert == 0)
                throw new RuntimeException("程序未知错误,信息添加失败");
        }
    }

    /**
     * 根据id删除记录
     *
     * @param id 要删除的记录的id
     */
    @Override
    @Transactional
    public void deleteById(int id) {
        if (id < 0) throw new IllegalArgumentException("id错误");

        InterviewPosition interviewPosition = interviewPositionMapper.selectById(id);

        Integer updateNum;

        updateNum= interviewPositionMapper.deleteById(id);

        if (updateNum != 1) {
            throw new RuntimeException("id有误，删除失败");
        }

        updateNum = reservationMapper.clearInterviewPositionUser(id);

        if (!Objects.equals(updateNum, interviewPosition.getContained())) {
            throw new RuntimeException("该地点待面试人员信息有误，面试地点删除失败，请联系管理员");
        }
    }

    /**
     * 根据用户id交换面试地点,乐观锁解决冲突修改问题
     * @param userId1 用户1的id
     * @param userId2 用户2的id
     * @return 交换是否成功
     */
    @Override
    @Transactional
    public void exchangeInterviewPosition(Integer userId1, Integer userId2) {

        ReservationPo reservationPo1 = reservationMapper.selectByUserId(userId1);
        ReservationPo reservationPo2 = reservationMapper.selectByUserId(userId2);

        if (reservationPo1 == null) {
            throw new RuntimeException("没有用户1的预约记录");
        }

        if (reservationPo2 == null) {
            throw new RuntimeException("没有用户2的预约记录");
        }

        if (reservationPo1.getInterviewId() == null) {
            throw new RuntimeException("用户1尚无面试地点，无法交换");
        }

        if (reservationPo2.getInterviewId() == null) {
            throw new RuntimeException("用户2尚无面试地点，无法交换");
        }

        if (Objects.equals(reservationPo1.getInterviewId(), reservationPo2.getInterviewId())) {
            throw new RuntimeException("两位用户面试地点相同，无需交换");
        }

        int temp = reservationPo1.getInterviewId();
        reservationPo1.setInterviewId(reservationPo2.getInterviewId());
        reservationPo2.setInterviewId(temp);

        Integer updatedNum1 = reservationMapper.updateByIdAndVersion(reservationPo1);
        Integer updatedNum2 = reservationMapper.updateByIdAndVersion(reservationPo2);

        if (updatedNum1 == 0 || updatedNum2 == 0) {
            throw new RuntimeException("修改冲突，交换失败");
        }
    }

    /**
     * 根据用户id和面试地点id修改用户面试地点,乐观锁解决冲突修改问题
     * @param userId     用户id
     * @param positionId 面试地点id
     * @return 修改是否成功
     */
    @Override
    @Transactional
    public void updateInterviewPosition(Integer userId, Integer positionId) {

        //获取预约记录
        ReservationPo reservationPo = reservationMapper.selectByUserId(userId);
        if (reservationPo == null) {
            throw new RuntimeException("没有用户1的预约记录");
        }

        //可能存在该用户尚无面试地点的情况
        InterviewPosition preInterviewPosition = null;

        //用户是否有面试地点
        if (reservationPo.getInterviewId() != null) {

            preInterviewPosition = interviewPositionMapper.selectById(reservationPo.getInterviewId());

            //数据库有问题,直接联系管理员
            if (preInterviewPosition == null) {
                throw new RuntimeException("该用户面试地点有误,请联系管理员");
            }

            if (Objects.equals(reservationPo.getInterviewId(), positionId)) {
                throw new RuntimeException("修改前后面试地点相同，无需修改");
            }
        }

        InterviewPosition newInterviewPosition = interviewPositionMapper.selectById(positionId);
        if (newInterviewPosition == null) {
            throw new RuntimeException("没有该面试地点");
        }

        if (Objects.equals(newInterviewPosition.getContained(), newInterviewPosition.getCapacity())) {
            throw new RuntimeException("新的面试地点已满,无法修改");
        }

        //修改操作影响的行数,意味着修改是否成功
        Integer updatedNum;

        //新地点人数+1
        newInterviewPosition.setContained(newInterviewPosition.getContained() + 1);
        updatedNum = interviewPositionMapper.updateByIdAndVersion(newInterviewPosition);
        if (updatedNum == 0) {
            throw new RuntimeException("修改冲突，面试地点修改失败");
        }

        //该用户不存在面试地点无需修改,存在面试地点时,旧地点人数-1
        if (preInterviewPosition != null) {
            preInterviewPosition.setContained(preInterviewPosition.getContained() - 1);
            updatedNum = interviewPositionMapper.updateByIdAndVersion(preInterviewPosition);
            if (updatedNum == 0) {
                throw new RuntimeException("修改冲突，面试地点修改失败");
            }
        }

        //修改面试地点
        reservationPo.setInterviewId(positionId);
        updatedNum = reservationMapper.updateByIdAndVersion(reservationPo);
        if (updatedNum == 0) {
            throw new RuntimeException("修改冲突，面试地点修改失败");
        }
    }

    /**
     * 为某个时间段的新生分配面试地点
     * @param interviewTime 待分配的时间段
     * @return 是否分配成功
     */
    @Override
    public void assignInterviewPosition(LocalDateTime interviewTime) {

        if (interviewTime == null)  throw new IllegalArgumentException("时间格式错误");

        List<InterviewPosition> interviewPositions = interviewPositionMapper.searchListNotFullByStartTime(interviewTime);
        List<ReservationPo> reservations = reservationMapper.selectUnassignedByInterviewTime(interviewTime);

        //为空代表已分配过或是无人报名,或尚未添加面试点
        if (interviewPositions.isEmpty() || reservations.isEmpty()) throw new RuntimeException("无需分配面试点");

        int num = 0;
        for (InterviewPosition interviewPosition : interviewPositions) {
            num += interviewPosition.getCapacity() - interviewPosition.getContained();
        }

        //报名人数小于可容纳人数,出现即为程序错误
        if (num < reservations.size()) throw new RuntimeException("报名人数超出面试点可容纳人数");

        assignInterviewPositionByFull(interviewPositions,reservations);

        //保存修改结果
        interviewPositionMapper.updateBatchById(interviewPositions);
        reservationMapper.updateBatchById(reservations);
    }

    /**
     * 为所有时间段的新生分配面试地点
     * @return 是否分配成功
     */
    @Override
    public void assignAllInterviewPosition() {
        List<LocalDateTime> interviewTimes = interviewPositionMapper.selectOpenedTimeListNotFull();

        //为空代表已分配过或是还不需要分配
        if (interviewTimes.isEmpty()) throw new RuntimeException("无需分配面试点");

        for (LocalDateTime interviewTime : interviewTimes) {
            assignInterviewPosition(interviewTime);
        }
    }

    /**
     * 为预约者分配面试地点,优先分配靠前的面试点
     * @param interviewPositions 面试地点列表
     * @param reservations 预约记录列表
     */
    public void assignInterviewPositionByAverage(List<InterviewPosition> interviewPositions, List<ReservationPo> reservations) {
        // TODO 实现优先分配靠前的面试点的分配方式
    }

    /**
     * 为预约者分配面试地点,优先平均分配
     * @param interviewPositions 面试地点列表
     * @param reservations 预约记录列表
     */
    public void assignInterviewPositionByFull(List<InterviewPosition> interviewPositions, List<ReservationPo> reservations) {
        int interviewPositionNum = interviewPositions.size();
        int reservationNum = reservations.size();

        //随机排序
        List<ReservationPo> sortedReservations = randomSort(reservations);

        //当前操作的对象
        ReservationPo curReservation;
        InterviewPosition curInterviewPosition;

        //循环变量
        int i = 0;
        int j = 0;
        //当前操作的面试地点对象的索引
        int curInterviewPositionIndex;
        while (i < reservationNum) {

            curInterviewPositionIndex = j++ % interviewPositionNum;
            curReservation = sortedReservations.get(i);
            curInterviewPosition = interviewPositions.get(curInterviewPositionIndex);

            if (curInterviewPosition.getContained() < curInterviewPosition.getCapacity()) {
                curReservation.setInterviewId(curInterviewPosition.getId());
                curInterviewPosition.setContained(curInterviewPosition.getContained() + 1);
                i++;
            }
        }
    }

    /**
     * 为需要分配预约人员随机排序
     *
     * @param reservations 待排序的序列
     */
    private void randomSort(ReservationPo[] reservations) {
        int nums = reservations.length;
        int randomNumber;
        ReservationPo temp;
        Random random = new Random();

        for (int i = 0; i < nums - 1; i++) {
            randomNumber = i + random.nextInt(nums - i);
            temp = reservations[i];
            reservations[i] =  reservations[randomNumber];
            reservations[randomNumber] = temp;
        }
    }

    /**
     * 为需要分配预约人员随机排序
     *
     * @param reservations 待排序的序列
     * @return 随机排序的结果
     */
    private List<ReservationPo> randomSort(List<ReservationPo> reservations) {
        ReservationPo[] reservationsArray = reservations.toArray(new ReservationPo[0]);
        randomSort(reservationsArray);
        return Arrays.stream(reservationsArray).collect(Collectors.toList());
    }

}
