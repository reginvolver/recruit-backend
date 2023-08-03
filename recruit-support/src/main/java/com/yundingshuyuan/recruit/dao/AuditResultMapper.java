package com.yundingshuyuan.recruit.dao;

import com.yundingshuyuan.recruit.domain.po.AuditResultPo;
import com.yundingshuyuan.recruit.domain.vo.AuditResultVo;

import java.util.List;


public interface AuditResultMapper extends BaseMapperPlus<AuditResultPo, AuditResultVo> {

    /**
     * 获取所有面试结果
     *
     * @return 面试结果 VO 集合
     */
    List<AuditResultPo> getAllResult();

    /**
     * 获取面试结果通过关键词
     *
     * @param keyword 关键词 (interviewee | groupId | studentId)
     * @param search  搜索内容
     * @return 面试结果 VO 集合
     */
    List<AuditResultPo> getResultByKeyword(String keyword, String search);

    /**
     * 获取已通过的面试结果 通过状态
     *
     * @param isPassed 是否通过状态
     * @return 面试结果 VO 集合
     */
    List<AuditResultPo> getPassedResultByPassState(boolean isPassed);

    /**
     * 的面试结果数量
     *
     * @return 面试结果 VO 集合
     */
    Long getResultAmount();

    /**
     * 获取已通过的面试结果数量
     *
     * @return 面试结果 VO 集合
     */
    Long getPassedResultAmount();

    /**
     * 获取未通过的面试结果数量
     *
     * @return 面试结果 VO 集合
     */
    Long getNotPassedResultAmount();

    /**
     * 修改通过结果
     *
     * @param studentId
     * @return 是否修改成功
     */
    int changePassedResultBySid(long studentId);

    /**
     * 修改通过结果
     *
     * @param userId
     * @return 是否修改成功
     */
    int changePassedResultByUid(long userId);

    /**
     * 一键通过所有审核
     *
     * @return 通过个数
     */
    int changeAllPassed();

}
