package com.yundingshuyuan.recruit.domain.vo;


import com.yundingshuyuan.recruit.domain.InterviewerInfo;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@AutoMapper(target = InterviewerInfo.class)
public class InterviewerInfoVo {

    private Integer groupId;

    private String username;

    private String password;


}
