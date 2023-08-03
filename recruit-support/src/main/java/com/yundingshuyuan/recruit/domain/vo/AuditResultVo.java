package com.yundingshuyuan.recruit.domain.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.yundingshuyuan.recruit.converter.AuditExcelConverter;
import com.yundingshuyuan.recruit.domain.po.AuditResultPo;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * 超级管理员-全部面试结果信息VO
 *
 * @author wys
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@AutoMapper(target = AuditResultPo.class)
public class AuditResultVo {

    /**
     * id
     */
    long id;
    /**
     * 用户id
     */
    @ExcelProperty("用户ID")
    long userId;
    /**
     * 学号
     */
    @ExcelProperty("学号")
    long studentId;
    /**
     * 面试者姓名
     */
    @ExcelProperty("面试者姓名")
    String interviewee;
    /**
     * 面试官组号
     */
    @ExcelProperty("面试官组号")
    long groupId;
    /**
     * 面试得分
     */
    @ExcelProperty("面试得分")
    BigDecimal score;
    /**
     * 是否通过
     */
    @ExcelProperty(value = "是否通过", converter = AuditExcelConverter.class)
    boolean passed;

}
