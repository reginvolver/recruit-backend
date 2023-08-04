package com.yundingshuyuan.recruit.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 用户信息表(UserInfo)表实体类
 *
 * @author makejava
 * @since 2023-08-02 09:23:57
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("user_info")
public class UserInfo {
    //用户id
    @TableId
    private Integer id;

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
    //乐观锁
    private Integer version;
    //逻辑删除
    private Integer deleted;
    //创建时间
    private Date createTime;
    //修改时间
    private Date updateTime;


}

