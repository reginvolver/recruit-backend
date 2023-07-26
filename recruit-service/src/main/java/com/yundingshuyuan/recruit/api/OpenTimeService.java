package com.yundingshuyuan.recruit.api;


import com.yundingshuyuan.recruit.domain.vo.OpenTimeInfoVo;

import java.time.LocalDate;
import java.util.List;

/**
 * 预约面试开放时间管理
 */
public interface OpenTimeService {

    /**
     * 设置一个开放预约时间
     *
     * @return
     */
    boolean setOneOpenTime(OpenTimeInfoVo info);

    /**
     * 设置一个开放预约时间
     *
     * @return
     */
    boolean setMultipleTime(OpenTimeInfoVo... info);

    /**
     * 删除一个开放预约面试时间
     *
     * @return 成功删除个数
     */
    int deleteOneOpenTime(long id);

    /**
     * 删除多个开放预约面试时间
     *
     * @return 成功删除个数
     */
    int deleteMutipleTime(long... id);

    /**
     * 修改一个开放预约面试时间
     *
     * @param info
     * @return
     */
    int changeOneOpenTime(OpenTimeInfoVo info);

    /**
     * 修改多个开放预约面试时间
     *
     * @param info
     * @return
     */
    boolean changeMutipleTime(OpenTimeInfoVo... info);

    /**
     * 根据日期获取当天的开放时间信息
     *
     * @return 信息对象
     */
    List<OpenTimeInfoVo> getOpenTimeInfoByDate(LocalDate date);

    /**
     * 获取全部的开放时间信息
     *
     * @return 信息对象
     */
    List<OpenTimeInfoVo> getAllOpenTimeInfo();

    /**
     * 分页获取开放预约时间
     *
     * @param current
     * @param size
     * @return
     */
    Object getPageOpenTimeInfo(long current, long size);
}
