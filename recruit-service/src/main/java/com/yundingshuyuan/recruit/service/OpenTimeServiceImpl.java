package com.yundingshuyuan.recruit.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yundingshuyuan.recruit.api.OpenTimeService;
import com.yundingshuyuan.recruit.dao.OpenTimeInfoMapper;
import com.yundingshuyuan.recruit.domain.po.OpenTimeInfoPo;
import com.yundingshuyuan.recruit.domain.vo.OpenTimeInfoVo;
import com.yundingshuyuan.recruit.service.verify.*;
import io.github.linpeilie.Converter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 预约面试开放时间管理
 *
 * @author wys
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class OpenTimeServiceImpl implements OpenTimeService {

    private final OpenTimeInfoMapper otiMapper;
    private final Converter converter;

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
        OpenTimeInfoPo insertInfo = converter.convert(info, OpenTimeInfoPo.class);
        otiMapper.insert(insertInfo);
        return true;
    }

    /**
     * 设置多个开放预约时间
     *
     * @param infos
     * @return
     */
    @Override
    public boolean batchSetOpenTime(OpenTimeInfoVo... infos) {
        // 参数校验
        for (OpenTimeInfoVo i : infos) {
            i.setReserved(0);
            commonValidation(i);
        }
        // 转换
        List<OpenTimeInfoPo> infoDos = Arrays.stream(infos).map(info -> converter.convert(info, OpenTimeInfoPo.class))
                .collect(Collectors.toList());
        return otiMapper.insertBatch(infoDos);
    }

    /**
     * 删除一个开放预约面试时间
     *
     * @param id
     * @return 成功删除个数
     */
    @Override
    public int deleteOneOpenTime(long id) {
        return otiMapper.deleteById(OpenTimeInfoPo.builder().id(id).build());
    }

    /**
     * 删除多个开放预约面试时间
     *
     * @param ids
     * @return 成功删除个数
     */
    @Override
    public int batchDeleteOpenTime(long... ids) {
        return otiMapper.deleteBatchIds(Collections.singletonList(ids));
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
        OpenTimeInfoPo infoDo = converter.convert(info, OpenTimeInfoPo.class);
        return otiMapper.updateById(infoDo);
    }

    /**
     * 修改多个面试时间
     *
     * @param infos
     * @return 修改成功否
     */
    @Override
    public boolean batchChangeOpenTime(OpenTimeInfoVo... infos) {
        // 参数校验
        for (OpenTimeInfoVo info : infos) {
            commonValidation(info);
        }
        // 转换
        List<OpenTimeInfoPo> infoDos = Arrays.stream(infos).map(info -> converter.convert(info, OpenTimeInfoPo.class))
                .collect(Collectors.toList());

        return otiMapper.updateBatchById(infoDos);
    }

    /**
     * 通过日期查到当天的开放预约时间
     *
     * @param date
     * @return 开放预约时间列表
     */
    @Override
    public List<OpenTimeInfoVo> getOpenTimeInfoByDate(LocalDate date) {
        // 参数校验 null
        LocalDateTime dateStart = date.atStartOfDay();
        LocalDateTime dateEnd = date.plusDays(1).atStartOfDay();
        return otiMapper.selectVoList(new QueryWrapper<OpenTimeInfoPo>()
                .between("end_time", dateStart, dateEnd));
    }

    /**
     * 获取所有开放预约时间
     *
     * @return
     */
    @Override
    public List<OpenTimeInfoVo> getAllOpenTimeInfo() {
        return otiMapper.selectVoList();
    }

    /**
     * 分页获取开放预约时间
     *
     * @param current
     * @param size
     * @return
     */
    @Override
    public IPage<OpenTimeInfoVo> getPageOpenTimeInfo(long current, long size) {
        return otiMapper.selectVoPage(new Page<>(current, size), new QueryWrapper<>());
    }

    /**
     * 常用参数校验
     *
     * @param info
     */
    private void commonValidation(OpenTimeInfoVo info) {
        new AbstractOpenTimeValidation.Builder()
                .add(new FutureValidation(info.getStartTime()))
                .add(new FutureValidation(info.getEndTime()))
                .add(new LegalTimePeriodValidation(info))
                .add(new ExceedCapacityValidation(info))
                .add(new NoTimeConflictValidation(info, this))
                .build().validate();
    }

}
