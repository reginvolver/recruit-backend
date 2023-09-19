package com.yundingshuyuan.recruit.domain;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReservationEntity {
    private Integer id;
    private LocalDateTime interviewTime;
    private Integer userId;
    private Integer interviewId;
    private String direction;
    private String name;
    private String qq;
    private String gender;
    private String location;

    public ReservationEntity(Integer id, LocalDateTime interviewTime, Integer userId, Integer interviewId,
                             String direction, String name, String qq, String gender, String location) {
        this.id = id;
        this.interviewTime = interviewTime;
        this.userId = userId;
        this.interviewId = interviewId;
        this.direction = direction;
        this.name = name;
        this.qq = qq;
        this.gender = gender;
        this.location = location;
    }

    // Getter and setter methods
}