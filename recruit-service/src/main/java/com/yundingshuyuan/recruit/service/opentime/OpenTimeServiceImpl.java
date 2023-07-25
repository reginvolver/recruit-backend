package com.yundingshuyuan.recruit.service.opentime;

import com.yundingshuyuan.recruit.api.OpenTimeService;
import com.yundingshuyuan.recruit.api.OpenTimeValidation;
import com.yundingshuyuan.recruit.dao.InterviewPositionMapper;
import com.yundingshuyuan.recruit.domain.OpenTimeInfo;
import com.yundingshuyuan.recruit.service.opentime.validations.FutureValidation;
import com.yundingshuyuan.recruit.service.opentime.validations.LegalTimePeriodValidation;
import com.yundingshuyuan.recruit.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * 预约面试开放时间管理
 */
@Service
@Slf4j
public class OpenTimeServiceImpl implements OpenTimeService {

    @Autowired
    private InterviewPositionMapper ipsMapper;


    private final String REDIS_ZSET_KEY = "OpenTime_ZSet";


    @Override
    public boolean setOpenTime(OpenTimeInfo info) {
        System.out.println("c: " + LocalDateTime.now());
        System.out.println("s: " + info.getStartTime());
        System.out.println("e: " + info.getEndTime());

        // 参数校验
        new OpenTimeValidationBuilder()
                .add(new FutureValidation(info.getStartTime()))
                .add(new FutureValidation(info.getEndTime()))
                .add(new LegalTimePeriodValidation(info))
                .build().validate();
        // 没问题存储
        RedisUtils redis = new RedisUtils();
        return false;
    }

}
