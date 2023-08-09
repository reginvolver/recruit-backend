package com.yundingshuyuan.recruit.domain.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.yundingshuyuan.recruit.domain.RegisterInfo;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@AutoMapper(target = RegisterInfo.class)
public class RegisterInfoVO {
    @TableField("user_id")
    private Integer userId;
    private String direction;
}
