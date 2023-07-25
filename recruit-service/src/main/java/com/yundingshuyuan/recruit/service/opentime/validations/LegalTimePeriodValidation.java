package com.yundingshuyuan.recruit.service.opentime.validations;

import com.yundingshuyuan.recruit.api.OpenTimeValidation;
import com.yundingshuyuan.recruit.domain.OpenTimeInfo;
import lombok.AllArgsConstructor;

import java.security.InvalidParameterException;
import java.time.LocalDateTime;

/**
 * 合法时间段检测
 * <p>1.startTime < endTime</p>
 * <p>2.该时间段与其他时间段无冲突(交集)</p>
 */
@AllArgsConstructor
public class LegalTimePeriodValidation extends OpenTimeValidation {

    private OpenTimeInfo openTimeInfo;

    @Override
    public boolean validate() {
        LocalDateTime startTime = openTimeInfo.getStartTime();
        LocalDateTime endTime = openTimeInfo.getEndTime();
        if (startTime.isAfter(endTime)) {throw new InvalidParameterException("面试开始时间不能晚于结束时间");}
        if (nextTask != null) {
            return nextTask.validate();
        }
        return true;
    }

}
