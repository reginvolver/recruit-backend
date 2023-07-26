package com.yundingshuyuan.recruit.service.otverify;

import com.yundingshuyuan.recruit.domain.vo.OpenTimeInfoVo;
import lombok.AllArgsConstructor;

import java.security.InvalidParameterException;

@AllArgsConstructor
public class ExceedCapacityValidation extends OpenTimeValidation {
    private OpenTimeInfoVo info;

    @Override
    public boolean validate() {
        if (info.getCapacity() < info.getReserved()) {
            throw new InvalidParameterException("已预定人数不能大于容量");
        }
        if (nextTask != null) {
            return nextTask.validate();
        }
        return true;
    }
}
