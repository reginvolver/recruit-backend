package com.yundingshuyuan.recruit.domain.vo;



import com.yundingshuyuan.recruit.domain.po.InterviewRecordPo;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @Author cr
 * @Date 2023/7/27 10:11
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@AutoMapper(target = InterviewRecordPo.class)
public class InterviewRecordVo {
    /**
     * 面试id
     */
    private Integer id;

    /**
     * 参加面试的用户id
     */
    private Integer userId;

    /**
     * 参加面试的用户name
     */
    private String userName;

    /**
     * 面试情况反馈,面评
     */
    private String faceback;

    /**
     * 面试得分
     */
    private BigDecimal score;

    /**
     * 面试官
     */
    private Integer group_id;

}
