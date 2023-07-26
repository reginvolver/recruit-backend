package com.yundingshuyuan.recruit.service.otverify;

import lombok.AllArgsConstructor;

import java.security.InvalidParameterException;
import java.time.LocalDateTime;


/**
 * 是否为未来时间
 */
@AllArgsConstructor
public class FutureValidation extends OpenTimeValidation {

    private LocalDateTime time;

    @Override
    public boolean validate() throws InvalidParameterException{
        LocalDateTime serveTime = LocalDateTime.now();
        if (time.isBefore(serveTime)){throw new InvalidParameterException("预约面试时间不能为过去的时间");}
        if (nextTask != null) {
            return nextTask.validate();
        }
        return true;
    }


}
