package com.yundingshuyuan.recruit.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.yundingshuyuan.recruit.domain.vo.UserInfoVo;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@TableName("user_info")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@AutoMapper(target = UserInfoVo.class)
public class UserInfo {

    @TableId
    private Integer id;

    @TableField("cloud_id")
    private Integer cloudId;

    private String name;
    private String gender;

    private String phone;
    private String email;
    @TableField("student_number")
    private String studentNumber;
    private String qq;

    private String major;
    @TableField("academy_id")
    private Integer academyId;
    @TableField("is_lectured")
    private Boolean isLectured;
    @TableField("is_passed")

    private Boolean isPassed;

    @TableField("is_admin")

    private Integer isAdmin;

    @TableField("qr_code")

    private String qrCode;

    @Version
    private Integer version;

    @TableLogic
    private Boolean deleted;
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

}
