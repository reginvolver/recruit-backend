package com.yundingshuyuan.recruit.domain;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName("interviewer_info")
public class InterviewerInfo {
    @TableField("group_id")
    @ExcelProperty("面试小组id")
    private Integer groupId;

    @TableField("interviewer_first_name")
    @ExcelProperty("第一面试官姓名")
    private String interviewerFirstName;
    @TableField("interviewer_second_name")
    @ExcelProperty("第二面试官姓名")
    private String interviewerSecondName;
    @TableField("group_username")
    @ExcelProperty("面试系统账号")
    private String groupUsername;
    @TableField("group_password")
    @ExcelProperty("面试系统密码")
    private String groupPassword;

    @ExcelIgnore
    @Version
    private Integer version;

    @ExcelIgnore
    @TableLogic
    private Boolean deleted;

    public InterviewerInfo(Integer groupId, String firstInterName, String secondInterName) {
        this.groupId = groupId;
        this.interviewerFirstName = firstInterName;
        this.interviewerSecondName = secondInterName;
    }
}
