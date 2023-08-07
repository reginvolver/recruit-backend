package com.yundingshuyuan.recruit.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 面试官信息表
 * </p>
 *
 * @author cr
 * @since 2023-07-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("interviewer_info")
public class InterviewerInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 组号
     */
    @TableId(value = "group_id", type = IdType.AUTO)
    private Integer groupId;

    /**
     * 该组登录时所用的用户名
     */
    private String groupUsername;

    /**
     * 该组登录时所用的密码
     */
    private String groupPassword;

    /**
     * 组中第一位面试官姓名
     */
    private String interviewerFirstName;

    /**
     * 组中第二位面试官姓名
     */
    private String interviewerSecondName;

    /**
     * 乐观锁
     */
    private Integer version;

    /**
     * 逻辑删除
     */
    @TableLogic
    private Boolean deleted;


}
