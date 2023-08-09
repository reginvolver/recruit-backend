package com.yundingshuyuan.recruit.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
@Builder
public class Academy {
    private Integer id;
    private String school;
    private String academy;

    private String detail;

}
