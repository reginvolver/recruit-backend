package com.yundingshuyuan.recruit.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoVo {

    //姓名
    private String name;
    //用户性别
    private String gender;
    //专业
    private String major;
    //学校id
    private String academyId;
    //学号
    private String studentNumber;
    //手机号
    private String phone;
    //邮箱
    private String email;
    //QQ号
    private String qq;
    //二维码
    private String qrCode;
    //是否参加过宣讲会
    private Integer isLectured;
    //是否通过面试 0 未通过 1 通过
    private Integer isPassed;
    //微信生成的openid
    private String cloudId;
}
