package com.yundingshuyuan.recruit.domain.vo;


import com.yundingshuyuan.recruit.domain.UserInfo;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
@AutoMapper(target = UserInfo.class)
@Builder
public class UserInfoVO {

    private Integer id;
    private Integer cloudId;
    private String name;
    private String gender;
    private String direction;
    private String phone;
    private String email;
    private String studentNumber;
    private String qq;

    private String major;

    private String qrCode;

    private String academy;
    private String school;
}
