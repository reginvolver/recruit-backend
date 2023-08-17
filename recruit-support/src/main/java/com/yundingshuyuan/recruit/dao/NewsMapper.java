package com.yundingshuyuan.recruit.dao;

import com.yundingshuyuan.recruit.domain.News;
import com.yundingshuyuan.recruit.domain.vo.NewsVo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface NewsMapper extends BaseMapperPlus<News, NewsVo> {

}
