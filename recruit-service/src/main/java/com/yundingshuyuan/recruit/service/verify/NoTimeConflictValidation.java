package com.yundingshuyuan.recruit.service.verify;

import com.alibaba.fastjson.JSON;
import com.yundingshuyuan.recruit.api.OpenTimeService;
import com.yundingshuyuan.recruit.domain.vo.OpenTimeInfoVo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.security.InvalidParameterException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 没有时间冲突校验
 */
@AllArgsConstructor
@Slf4j
public class NoTimeConflictValidation extends AbstractOpenTimeValidation {
    private OpenTimeInfoVo info;
    private OpenTimeService otService;

    @Override
    public boolean validate() {
        // 调取当天所有数据
        LocalDate localDate = info.getStartTime().toLocalDate();
        List<OpenTimeInfoVo> data = otService.getOpenTimeInfoByDate(localDate);
        // 加入比较
        List<OpenTimeInfoVo> conflict = data.stream().filter(item -> {
            if (item.getStartTime().isBefore(info.getStartTime()) && item.getEndTime().isBefore(info.getStartTime())) {
                return false;
            }
            return !item.getStartTime().isAfter(info.getEndTime()) || !item.getEndTime().isAfter(info.getEndTime());
        }).collect(Collectors.toList());
        // 结果抛出
        if (!conflict.isEmpty()) {
            throw new InvalidParameterException("冲突时间:" + JSON.toJSONString(conflict));
        }
        // 继续
        if (nextTask != null) {
            return nextTask.validate();
        }
        return true;
    }
}
