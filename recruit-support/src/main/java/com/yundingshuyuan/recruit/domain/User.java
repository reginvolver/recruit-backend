package com.yundingshuyuan.recruit.domain;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户表
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    @ExcelProperty("用户id")
    private Integer id;

    @ExcelProperty("姓名")
    private String name;


}
