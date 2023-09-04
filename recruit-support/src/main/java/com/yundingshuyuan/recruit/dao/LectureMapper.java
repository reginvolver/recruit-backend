package com.yundingshuyuan.recruit.dao;

import com.yundingshuyuan.recruit.domain.Lecture;
import com.yundingshuyuan.recruit.domain.vo.LectureVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface LectureMapper extends BaseMapperPlus<Lecture, LectureVo> {

    LectureVo lectureById(Integer lectureId);

    LectureVo theLeast();

    List<LectureVo> allLecture();


}
