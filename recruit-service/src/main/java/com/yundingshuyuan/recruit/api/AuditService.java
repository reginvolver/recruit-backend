package com.yundingshuyuan.recruit.api;

import com.yundingshuyuan.recruit.domain.vo.AuditResultVo;
import com.yundingshuyuan.recruit.domain.vo.OutComeAmount;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


public interface AuditService {

    /**
     * 获取所有面试结果
     *
     * @return 面试结果 VO 集合
     */
    List<AuditResultVo> getAllResult();

    /**
     * 获取面试结果通过关键词
     *
     * @param keyword 关键词 (interviewee | groupId | studentId)
     * @param search  搜索内容
     * @return 面试结果 VO 集合
     */
    List<AuditResultVo> getResultByKeyword(String keyword, String search);

    /**
     * 获取已通过的面试结果
     *
     * @param isPassed
     * @return List 面试结果 VO 集合
     */
    List<AuditResultVo> getPassedResultByPassState(boolean isPassed);

    /**
     * 每种状态的面试结果数量统计
     *
     * @return
     */
    OutComeAmount getOutcomeAmount();

    /**
     * 获取所有结果导出到 Excel表格
     *
     * @param response
     * @param fileName 文件名
     * @throws IOException
     */
    void getAllResultExportToExcel(HttpServletResponse response, String fileName) throws IOException;


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
