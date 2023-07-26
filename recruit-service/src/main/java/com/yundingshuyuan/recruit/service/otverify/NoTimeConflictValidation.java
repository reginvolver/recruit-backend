package com.yundingshuyuan.recruit.service.otverify;

import com.yundingshuyuan.recruit.api.OpenTimeService;
import com.yundingshuyuan.recruit.domain.vo.OpenTimeInfoVo;
import lombok.AllArgsConstructor;

import java.security.InvalidParameterException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public class NoTimeConflictValidation extends OpenTimeValidation {
    private OpenTimeInfoVo info;

    private OpenTimeService otService;

    @Override
    public boolean validate() {
        // 调取当天所有数据
        LocalDate localDate = info.getStartTime().toLocalDate();
        List<OpenTimeInfoVo> data = otService.getOpenTimeInfoByDate(localDate);
        // 加入比较
        data.add(info);
        Object[] startTimes = data.stream()
                .map(o -> o.getStartTime().toString())
                .collect(Collectors.toList()).toArray();
        Object[] endTimes = data.stream()
                .map(o -> o.getEndTime().toString())
                .collect(Collectors.toList()).toArray();
        // 内部交集判断算法
        Arrays.sort(startTimes);
        Arrays.sort(endTimes);
        for (int i = 1; i < startTimes.length; i++) {
            if (((String) startTimes[i]).compareTo((String) endTimes[i - 1]) < 0) {
                throw new InvalidParameterException(
                        String.format("面试时间冲突:\n%s - %s confilct %s - %s",
                                startTimes[i - 1], endTimes[i - 1],
                                startTimes[i], endTimes[i])
                );
            }
        }
        // 继续
        if (nextTask != null) {
            return nextTask.validate();
        }
        return true;
    }
}
