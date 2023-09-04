package com.yundingshuyuan.recruit.domain.vo;


import com.yundingshuyuan.recruit.domain.InterviewerInfo;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@AutoMapper(target = InterviewerInfo.class)
public class InterviewerInfoVo {
    /**
     * 组号
     */
    private Integer groupId;

    /**
     * 该组登录时所用的用户名
     */
    private String groupUsername;

    /**
     * 该组登录时所用的密码
     */
    private String groupPassword;

    /**
     * 组中第一位面试官姓名
     */
    private String interviewerFirstName;

    /**
     * 组中第二位面试官姓名
     */
    private String interviewerSecondName;

}
