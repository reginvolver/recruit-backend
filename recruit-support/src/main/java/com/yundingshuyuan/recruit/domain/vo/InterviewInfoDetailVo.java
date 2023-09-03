package com.yundingshuyuan.recruit.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author cr
 * @Date 2023/7/27 17:00
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InterviewInfoDetailVo {

    /**
     * 用户id
     */
    private Integer id;

    /**
     * 是否参加过宣讲会
     */
    private Boolean isLectured;

    /**
     * 姓名
     */
    private String name;

    /**
     * 专业
     */
    private String major;

    /**
     * 书院
     */
    private String academy;

    /**
     * 学号
     */
    private String studentNumber;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * QQ号
     */
    private String qq;

    /**
     * 申请书照片（多张申请书照片）
     */
    private List<String> urls;
}
