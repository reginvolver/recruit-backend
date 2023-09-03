package com.yundingshuyuan.recruit.web.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.yundingshuyuan.recruit.api.ICollegeService;
import com.yundingshuyuan.recruit.domain.Academy;
import com.yundingshuyuan.vo.BasicResultVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@Tag(name = "书院相关接口")
@Slf4j
@RequestMapping("/miniapp/college")
public class CollegeController {

    @Autowired
    private ICollegeService collegeService;

    /**
     * 选择书院
     * @param academyId
     * @return
     */
    @Operation(summary = "选择书院")
    @PostMapping("/selectAcademy")
    public BasicResultVO SelectAcademy(@RequestBody Integer academyId){
        return collegeService.selectAcademy(academyId);
    }

    @Operation(summary = "展示书院信息")
    @GetMapping("/showAcademy")
    public BasicResultVO<Academy> ShowAcademy(Integer id){
        return collegeService.showAcademy(id);
    }

    @Operation(summary = "展示新闻")
    @GetMapping("/showNews")
    public BasicResultVO showNews(Integer academyId) {
        return collegeService.showNews(academyId);
    }
}
