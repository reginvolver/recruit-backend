package com.yundingshuyuan.recruit.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.time.LocalDateTime;

/**
 * 面试时间
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OpenTimeInfo {

    /**
     * 开放起始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime startTime;
    /**
     * 开放结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime endTime;

    private Integer capacity;

}
