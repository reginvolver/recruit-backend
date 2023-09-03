package com.yundingshuyuan.recruit.domain.vo;



import com.yundingshuyuan.recruit.domain.LectureTicket;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author cr
 * @Date 2023/8/3 22:12
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@AutoMapper(target = LectureTicket.class)
public class LectureTicketVo {

    /**
     * 抢票记录id
     */
    private Integer id;

    /**
     * 抢票用户的id
     */
    private Integer userId;

    /**
     * 宣讲会的id
     */
    private Integer lectureId;

    /**
     * 该用户本场宣讲会状态
     * 0 未扫码
     * 1 已扫码
     */
    private Integer status;


}
