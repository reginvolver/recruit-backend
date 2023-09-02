package com.yundingshuyuan.recruit.service;

import com.yundingshuyuan.recruit.domain.po.OpenTimeInfoPo;
import com.yundingshuyuan.recruit.domain.po.ReservationPo;
import com.yundingshuyuan.recruit.dao.InterviewTimeMapper;
import com.yundingshuyuan.recruit.dao.ReservationMapper;
import com.yundingshuyuan.recruit.api.InterviewTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

/**
 预约面试时间
 */
@Service
public class InterviewTimeServiceImpl implements InterviewTimeService {
    private final InterviewTimeMapper interviewTimeMapper;
    private final ReservationMapper reservationMapper;

    // 添加RedisTemplate依赖
    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    public InterviewTimeServiceImpl(InterviewTimeMapper interviewTimeMapper, ReservationMapper reservationMapper) {
        this.interviewTimeMapper = interviewTimeMapper;
        this.reservationMapper = reservationMapper;
    }

    @Override
    public boolean reserveInterview(String cloudId, LocalDateTime startTime) {
        // 根据 cloudId 获取对应的 userId
        Integer userId = reservationMapper.getUserIdByCloudId(cloudId);
        if (userId == null) {
            throw new RuntimeException("预约失败，用户信息不存在");
        }

        // 检查是否已存在预约记录
        boolean hasReservation = reservationMapper.hasReservation(userId);
        if (hasReservation) {
            throw new RuntimeException("您已经预约过面试时间");
        }

        // 查询面试时间段的信息
        OpenTimeInfoPo interviewTime = interviewTimeMapper.getInterviewTimeByStartTime(startTime);
        if (interviewTime == null) {
            throw new RuntimeException("预约失败，面试时间不存在");
        }

        // 使用Redis的分布式锁保护预约操作的原子性
        String lockKey = "interview:lock";
        Boolean acquiredLock = redisTemplate.opsForValue().setIfAbsent(lockKey, 1, 30, TimeUnit.SECONDS);
        if (acquiredLock != null && acquiredLock) {
            try {
                // 检查面试时间段是否已满
                if (interviewTime.getReserved() >= interviewTime.getCapacity()) {
                    throw new RuntimeException("预约失败，该时间段已经预约满了，请选择其他时间");
                }

                // 向 reservation 表插入一条预约记录，并更新面试时间段的信息中的已预约人数
                ReservationPo reservation = new ReservationPo();
                reservation.setUserId(userId);
                reservation.setInterviewTime(interviewTime.getStartTime());
                reservationMapper.insert(reservation);

                // 使用Redis的原子操作增加已预约人数
                String key = "interview:time:" + interviewTime.getId();
                redisTemplate.opsForValue().increment(key, 1);

                return true;
            } finally {
                // 释放锁
                redisTemplate.delete(lockKey);
            }
        } else {
            throw new RuntimeException("系统繁忙，请稍后再试");
        }
    }
}