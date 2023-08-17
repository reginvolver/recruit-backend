package com.yundingshuyuan.recruit.web.controller;

import com.yundingshuyuan.recruit.api.IDisplayService;
import com.yundingshuyuan.vo.BasicResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Slf4j
@RequestMapping("/display")
public class DisplayController {

    @Autowired
    private IDisplayService displayService;

    @GetMapping("/showNews")
    public BasicResultVO showNews() {
        return displayService.showNews();
    }

    @GetMapping("/showAcademy")
    public BasicResultVO showAcademy() {
        return displayService.showAcademy();
    }
}
