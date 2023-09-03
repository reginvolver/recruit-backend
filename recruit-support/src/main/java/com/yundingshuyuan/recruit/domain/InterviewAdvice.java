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
 * 面试官意见表
 * </p>
 *
 * @author cr
 * @since 2023-07-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("interview_advice")
public class InterviewAdvice implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 初筛记录id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 参加面试的用户id
     */
    private Integer userId;

    /**
     * 参加面试的用户姓名
     */
    private String userName;

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
