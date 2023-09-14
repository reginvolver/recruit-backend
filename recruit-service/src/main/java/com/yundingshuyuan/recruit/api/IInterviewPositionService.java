package com.yundingshuyuan.recruit.api;

import com.yundingshuyuan.recruit.domain.vo.InterviewPositionVo;
import com.yundingshuyuan.vo.BasicResultVO;

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
    void addInterviewPosition(InterviewPositionVo interviewPositionVo);

    /**
     * 为某个时间段的新生分配面试地点
     * @param interviewTime 待分配的时间段
     * @return 是否分配成功
     */
    void assignInterviewPosition(LocalDateTime interviewTime);

    /**
     * 为所有时间段的新生分配面试地点
     * @return 是否分配成功
     */
    void assignAllInterviewPosition();

    /**
     * 根据id删除记录
     *
     * @param id 要删除的记录的id
     * @return 是否删除, 1是0否
     */
    void deleteById(int id);

    /**
     * 根据用户id交换面试地点
     * @param userId1 用户1的id
     * @param userId2 用户2的id
     * @return 交换是否成功
     */
    void exchangeInterviewPosition(Integer userId1, Integer userId2);

    /**
     * 根据 用户id和面试地点id修改用户面试地点
     * @param userId 用户id
     * @param positionId 面试地点id
     * @return 修改是否成功
     */
    void updateInterviewPosition(Integer userId, Integer positionId);
}
