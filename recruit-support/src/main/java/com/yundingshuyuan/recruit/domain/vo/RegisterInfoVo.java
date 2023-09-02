package com.yundingshuyuan.recruit.domain.vo;


import com.yundingshuyuan.recruit.domain.po.RegisterInfoPo;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@AutoMapper(target = RegisterInfoPo.class)
public class RegisterInfoVo {
    private Integer userId;
    private String direction;
}
