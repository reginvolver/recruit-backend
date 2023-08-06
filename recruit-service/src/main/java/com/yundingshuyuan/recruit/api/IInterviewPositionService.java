package com.yundingshuyuan.recruit.api;

import com.yundingshuyuan.recruit.domain.vo.InterviewPositionVo;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 面试场次表 服务类
 * </p>
 *
 * @author NSC
 * @since 2023-07-31
 */
public interface IInterviewPositionService {

    /**
     * 获取全部的面试地点
     * @return 返回获取到的面试地点列表
     */
    List<InterviewPositionVo> selectAll();

    /**
     * 添加新的面试场次
     * @param interviewPositionVo 新面试场次DTO
     * @return 分配是否成功
     */
    boolean addInterviewPosition(InterviewPositionVo interviewPositionVo);

    /**
     * 为某个时间段的新生分配面试地点
     * @param interviewTime 待分配的时间段
     * @return 是否分配成功
     */
    boolean assignInterviewPosition(LocalDateTime interviewTime);

    /**
     * 为所有时间段的新生分配面试地点
     * @return 是否分配成功
     */
    boolean assignAllInterviewPosition();

    /**
     * 根据id删除记录
     *
     * @param id 要删除的记录的id
     * @return 是否删除, 1是0否
     */
    boolean deleteById(int id);
}
