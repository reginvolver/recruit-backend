package com.yundingshuyuan.recruit.service.opentime.validations;

import com.yundingshuyuan.recruit.api.OpenTimeValidation;
import com.yundingshuyuan.recruit.domain.OpenTimeInfo;
import java.security.InvalidParameterException;
import java.time.LocalDateTime;

public class NoTimeConfilctValidation extends OpenTimeValidation {
    private OpenTimeInfo openTimeInfo;

    @Override
    public boolean validate() {
        LocalDateTime startTime = openTimeInfo.getStartTime();
        LocalDateTime endTime = openTimeInfo.getEndTime();
        if (startTime.isBefore(endTime)) {return true;}
        throw new InvalidParameterException("面试开始时间不能晚于结束时间");
    }
}
