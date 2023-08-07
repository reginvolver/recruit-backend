package com.yundingshuyuan.recruit.domain.vo;



import com.yundingshuyuan.recruit.domain.UserInfo;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author cr
 * @Date 2023/7/26 23:41
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@AutoMapper(target = UserInfo.class)
public class UserInfoVo {
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
     * 二维码
     */
    private String qrCode;

}
