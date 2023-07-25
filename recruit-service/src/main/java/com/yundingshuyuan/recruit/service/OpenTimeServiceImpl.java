package com.yundingshuyuan.recruit.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yundingshuyuan.recruit.api.OpenTimeService;
import com.yundingshuyuan.recruit.dao.OpenTimeInfoMapper;
import com.yundingshuyuan.recruit.domain.OpenTimeInfo;
import com.yundingshuyuan.recruit.domain.vo.OpenTimeInfoVo;
import com.yundingshuyuan.recruit.service.otverify.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 预约面试开放时间管理
 */
@Service
@Slf4j
public class OpenTimeServiceImpl implements OpenTimeService {

    @Autowired
    private OpenTimeInfoMapper ipsMapper;

    /**
     * 设置一个开放预约时间
     *
     * @param info
     * @return
     */
    @Override
    public boolean setOneOpenTime(OpenTimeInfoVo info) {
        info.setReserved(0);
        // 参数校验
        commonValidation(info);
        // 转换
        OpenTimeInfo insertInfo = OpenTimeInfo.builder()
                .startTime(info.getStartTime()).endTime(info.getEndTime())
                .reserved(info.getReserved()).capacity(info.getCapacity())
                .version(1).build();
        ipsMapper.insert(insertInfo);
        return true;
    }

    /**
     * 设置多个开放预约时间
     *
     * @param info
     * @return
     */
    @Override
    public boolean setMultipleTime(OpenTimeInfoVo... infos) {
        // 参数校验
        for (OpenTimeInfoVo i : infos) {
            i.setReserved(0);
            commonValidation(i);
        }
        // 转换
        List<OpenTimeInfo> infoDos = Arrays.stream(infos).map(info -> OpenTimeInfo.builder().id(info.getId())
                .startTime(info.getStartTime()).endTime(info.getEndTime())
                .capacity(info.getCapacity()).reserved(info.getReserved())
                .version(1).build()).collect(Collectors.toList());
        return ipsMapper.insertBatch(infoDos);
    }

    /**
     * 删除一个开放预约面试时间
     *
     * @param id
     * @return 成功删除个数
     */
    @Override
    public int deleteOneOpenTime(long id) {
        return ipsMapper.deleteById(OpenTimeInfo.builder().id(id).build());
    }

    /**
     * 删除多个开放预约面试时间
     *
     * @param ids
     * @return 成功删除个数
     */
    @Override
    public int deleteMutipleTime(long... ids) {
        return ipsMapper.deleteBatchIds(Arrays.asList(ids));
    }

    /**
     * 修改一个面试时间
     *
     * @param info
     * @return 修改个数
     */
    @Override
    public int changeOneOpenTime(OpenTimeInfoVo info) {
        // 参数校验
        commonValidation(info);
        // 修改
        OpenTimeInfo infoDo = OpenTimeInfo.builder().id(info.getId())
                .startTime(info.getStartTime()).endTime(info.getEndTime())
                .capacity(info.getCapacity()).reserved(info.getReserved())
                .build();
        return ipsMapper.updateById(infoDo);
    }

    /**
     * 修改多个面试时间
     *
     * @param infos
     * @return 修改成功否
     */
    @Override
    public boolean changeMutipleTime(OpenTimeInfoVo... infos) {
        // 参数校验
        for (OpenTimeInfoVo info : infos) {
            commonValidation(info);
        }
        // 转换
        List<OpenTimeInfo> infoDos = Arrays.stream(infos).map(info -> OpenTimeInfo.builder().id(info.getId())
                .startTime(info.getStartTime()).endTime(info.getEndTime())
                .capacity(info.getCapacity()).reserved(info.getReserved())
                .build()).collect(Collectors.toList());

        return ipsMapper.updateBatchById(infoDos);
    }

    /**
     * 通过日期查到当天的开放预约时间
     *
     * @param date
     * @return
     */
    @Override
    public List<OpenTimeInfoVo> getOpenTimeInfoByDate(LocalDate date) {
        // 参数校验 null
        LocalDateTime dateStart = date.atStartOfDay();
        LocalDateTime dateEnd = date.plusDays(1).atStartOfDay();
        return ipsMapper.selectVoList(new QueryWrapper<OpenTimeInfo>()
                .between("start_time", dateStart, dateEnd));
    }

    /**
     * 获取所有开放预约时间
     *
     * @return
     */
    @Override
    public List<OpenTimeInfoVo> getAllOpenTimeInfo() {
        return ipsMapper.selectVoList();
    }

    /**
     * 分页获取开放预约时间
     *
     * @param current
     * @param size
     * @return
     */
    @Override
    public Object getPageOpenTimeInfo(long current, long size) {
        return ipsMapper.selectVoPage(new Page<>(current, size), new QueryWrapper<>());
    }

    /**
     * 常用参数校验
     *
     * @param info
     */
    private void commonValidation(OpenTimeInfoVo info) {
        new OpenTimeValidationBuilder()
                .add(new FutureValidation(info.getStartTime()))
                .add(new FutureValidation(info.getEndTime()))
                .add(new LegalTimePeriodValidation(info))
                .add(new ExceedCapacityValidation(info))
                .add(new NoTimeConfilctValidation(info))
                .build().validate();
    }

}
