package com.yundingshuyuan.recruit.domain.vo;


import com.yundingshuyuan.recruit.domain.InterviewAdvice;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author cr
 * @Date 2023/7/28 17:41
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@AutoMapper(target = InterviewAdvice.class)
public class InterviewAdviceVo {
    /**
     * 初筛记录id
     */
    private Integer id;
    /**
     * 参加面试的用户id
     */
    private Integer userId;

    /**
     * 参加面试的用户姓名
     */
    private String userName;
}
