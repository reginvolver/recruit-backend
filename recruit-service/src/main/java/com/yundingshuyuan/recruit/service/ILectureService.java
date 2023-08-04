package com.yundingshuyuan.recruit.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yundingshuyuan.recruit.domain.Lecture;
import com.yundingshuyuan.vo.BasicResultVO;

public interface ILectureService extends IService<Lecture> {

    /**
     * 发布宣讲会，将前端传的数据存储在数据库中
     *
     * @param lecture
     * @return
     */
    public BasicResultVO release(Lecture lecture);

    /**
     * 返回所有宣讲会的信息
     *
     * @return
     */
    public BasicResultVO showAll();

}
