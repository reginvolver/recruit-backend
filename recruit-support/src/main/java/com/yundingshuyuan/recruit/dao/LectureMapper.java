package com.yundingshuyuan.recruit.dao;



import com.yundingshuyuan.recruit.domain.Lecture;
import com.yundingshuyuan.recruit.domain.vo.LectureVo;

import java.util.List;

/**
 * <p>
 * 宣讲会表 Mapper 接口
 * </p>
 *
 * @author cr
 * @since 2023-08-03
 */
public interface LectureMapper extends BaseMapperPlus<Lecture, LectureVo> {

    LectureVo lectureById(Integer lectureId);

    LectureVo theLeast();

    List<LectureVo> allLecture();

}
