package com.yundingshuyuan.recruit.service.verify;

import com.yundingshuyuan.recruit.domain.vo.OpenTimeInfoVo;
import lombok.AllArgsConstructor;

import java.security.InvalidParameterException;
import java.time.LocalDateTime;

/**
 * 合法时间段检测
 * <p>1.startTime < endTime</p>
 */
@AllArgsConstructor
public class LegalTimePeriodValidation extends AbstractOpenTimeValidation {

    private OpenTimeInfoVo openTimeInfo;

    @Override
    public boolean validate() {
        LocalDateTime startTime = openTimeInfo.getStartTime();
        LocalDateTime endTime = openTimeInfo.getEndTime();
        if (startTime.isAfter(endTime)) {
            throw new InvalidParameterException("面试开始时间不能晚于结束时间");
        }
        if (!startTime.toLocalDate().isEqual(endTime.toLocalDate())) {
            throw new InvalidParameterException("面试必须在同一天进行");
        }
        if (nextTask != null) {
            return nextTask.validate();
        }
        return true;
    }

}
