package com.yundingshuyuan.recruit.api;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yundingshuyuan.recruit.domain.vo.OpenTimeInfoVo;

import java.time.LocalDate;
import java.util.List;

/**
 * 预约面试开放时间管理
 *
 * @author wys
 */
public interface OpenTimeService {

    /**
     * 设置一个开放预约时间
     *
     * @param info 开放预约面试时间对象
     * @return
     */
    boolean setOneOpenTime(OpenTimeInfoVo info);

    /**
     * 设置一个开放预约时间
     *
     * @param infos 多个开放预约面试时间对象
     * @return
     */
    boolean batchSetOpenTime(OpenTimeInfoVo... infos);

    /**
     * 删除一个开放预约面试时间
     *
     * @param id 开放预约面试时间对应 id
     * @return 成功删除个数
     */
    int deleteOneOpenTime(long id);

    /**
     * 删除多个开放预约面试时间
     *
     * @param id 多个开放预约面试时间对应id
     * @return 成功删除个数
     */
    int batchDeleteOpenTime(long... id);

    /**
     * 修改一个开放预约面试时间
     *
     * @param info
     * @return 修改成功个数
     */
    int changeOneOpenTime(OpenTimeInfoVo info);

    /**
     * 修改多个开放预约面试时间
     *
     * @param info
     * @return 是否修改成功
     */
    boolean batchChangeOpenTime(OpenTimeInfoVo... info);

    /**
     * 根据日期获取当天的开放时间信息
     * @param date 日期
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
    IPage<OpenTimeInfoVo> getPageOpenTimeInfo(long current, long size);
}
