package com.yundingshuyuan.recruit.service.verify;

import cn.hutool.core.util.StrUtil;
import lombok.AllArgsConstructor;

import java.security.InvalidParameterException;
import java.time.LocalDateTime;


/**
 * 是否为未来时间
 */
@AllArgsConstructor
public class FutureValidation extends AbstractOpenTimeValidation {

    private LocalDateTime time;

    @Override
    public boolean validate() throws InvalidParameterException {
        LocalDateTime serveTime = LocalDateTime.now();
        if (time.isBefore(serveTime)) {
            throw new InvalidParameterException(
                    StrUtil.format("current:{} > scheduled:{}", serveTime, time));
        }
        if (nextTask != null) {
            return nextTask.validate();
        }
        return true;
    }


}
