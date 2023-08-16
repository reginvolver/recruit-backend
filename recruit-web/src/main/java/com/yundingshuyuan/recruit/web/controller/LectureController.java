package com.yundingshuyuan.recruit.web.controller;

import com.yundingshuyuan.recruit.api.ILectureService;
import com.yundingshuyuan.recruit.domain.Lecture;
import com.yundingshuyuan.vo.BasicResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@Slf4j
@RequestMapping("/lecture")
public class LectureController {

    @Resource
    private ILectureService lectureService;


    /**
     * 发布宣讲会
     *
     * @param lecture
     * @return
     */
    @PostMapping("/release")
    public BasicResultVO release(@RequestBody Lecture lecture) {

        return lectureService.release(lecture);
    }

    /**
     * 展示全部宣讲会
     *
     * @return
     */
    @GetMapping("/showAll")
    public BasicResultVO showAll() {
        return lectureService.showAll();
    }
}
