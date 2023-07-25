package com.yundingshuyuan.recruit.domain.po;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.mzt.logapi.beans.LogRecord;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.util.Date;

/**
 * Description:
 * Author: NSC
 * Created: 2023/7/17 18:14
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName("execution_log")
public class LogRecordPO {

    public static LogRecordPO toPo(LogRecord logRecord) {
        return LogRecordPO.builder()
                .tenant(logRecord.getTenant())
                .type(logRecord.getType())
                .subType(logRecord.getSubType())
                .bizNo(logRecord.getBizNo())
                .operator(logRecord.getOperator())
                .action(logRecord.getAction())
                .createTime(logRecord.getCreateTime())
                .fail(logRecord.isFail())
                .extra(logRecord.getExtra())
                .codeVariable(JSON.toJSONString(logRecord.getCodeVariable()))
                .build();
    }

    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 租户
     */
    private String tenant;

    /**
     * 保存的操作日志的类型，比如：订单类型、商品类型
     *
     * @since 2.0.0 从 prefix 修改为了type
     */
    @NotBlank(message = "type required")
    @Length(max = 200, message = "type max length is 200")
    private String type;
    /**
     * 日志的子类型，比如订单的C端日志，和订单的B端日志，type都是订单类型，但是子类型不一样
     * @since 2.0.0 从 category 修改为 subtype
     */
    @TableField("sub_type")
    private String subType;

    /**
     * 日志绑定的业务标识
     */
    @NotBlank(message = "bizNo required")
    @Length(max = 200, message = "bizNo max length is 200")
    @TableField("biz_no")
    private String bizNo;
    /**
     * 操作人
     */
    @NotBlank(message = "operator required")
    @Length(max = 63, message = "operator max length 63")
    private String operator;

    /**
     * 日志内容
     */
    @NotBlank(message = "opAction required")
    @Length(max = 511, message = "operator max length 511")
    private String action;
    /**
     * 记录是否是操作失败的日志
     */
    private Boolean fail;
    /**
     * 日志的创建时间
     */
    @TableField("create_time")
    private Date createTime;
    /**
     * 日志的额外信息
     *
     * @since 2.0.0 从detail 修改为extra
     */
    private String extra;

    /**
     * 打印日志的代码信息
     * CodeVariableType 日志记录的ClassName、MethodName
     */
    @TableField("code_variable")
    private String codeVariable;

}
