package com.yundingshuyuan.recruit.api;

import com.yundingshuyuan.recruit.domain.vo.DirectionCountVo;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 报名信息统计
 */
public interface DirectionCountService {
    /**
     * 统计各方向报名人数，当日报名人数，男女比例
     * @return
     */
    List<DirectionCountVo> getDirectionCounts();

    /**
     *统计总的每一天的报名人数
     * @return
     */
    List<Map<LocalDate, Integer>> getDateCounts();
    /**
     *  *统计总的报名人数,提交申请书的人数
     * @return
     */
    Map<String, Object> getTotalCounts();
}