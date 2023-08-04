package com.yundingshuyuan.recruit.domain.vo;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AcademyVo {
    //学校id
    @TableId
    private Integer id;

    //学校名称
    private String school;
    //书院名称
    private String academy;
    //书院简介
    private String detail;
}
