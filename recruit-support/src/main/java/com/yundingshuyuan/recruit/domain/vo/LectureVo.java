package com.yundingshuyuan.recruit.domain.vo;

import com.yundingshuyuan.recruit.domain.Lecture;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @Author cr
 * @Date 2023/8/3 22:29
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@AutoMapper(target = Lecture.class)
public class LectureVo {

    /**
     * 宣讲会id
     */
    private Integer lectureId;

    /**
     * 宣讲人
     */
    private String speaker;

    /**
     * 宣讲主题
     */
    private String lectureTheme;

    /**
     * 宣讲时间
     */
    private LocalDateTime lectureTime;

    /**
     * 参会人数
     */
    private Integer ticketNumber;

    /**
     * 剩余票数
     */
    private Integer ticketRemain;

    /**
     * 宣讲会排序
     */
    private Integer lectureOrder;


}
