package com.yundingshuyuan.recruit.domain;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * (Academy)表实体类
 *
 * @author makejava
 * @since 2023-08-01 10:12:09
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("academy")
public class Academy {
    //学校id
    @TableId
    private Integer id;

    //学校名称
    private String school;
    //书院名称
    private String academy;
    //书院简介
    private String detail;
    //逻辑删除
    private Integer deleted;
    //创建时间
    private Date createTime;
    //修改时间
    private Date updateTime;

}

