package com.yundingshuyuan.recruit.dao;

import com.yundingshuyuan.recruit.domain.InterviewerInfo;
import com.yundingshuyuan.recruit.domain.vo.InterviewerInfoVo;

/**
 * <p>
 * 面试官信息表 Mapper 接口
 * </p>
 *
 * @author cr
 * @since 2023-07-28
 */
public interface InterviewerInfoMapper extends BaseMapperPlus<InterviewerInfo, InterviewerInfoVo> {


    int minCount();

    int incrCount(int groupId);

    int decrCount(int groupId);
}
