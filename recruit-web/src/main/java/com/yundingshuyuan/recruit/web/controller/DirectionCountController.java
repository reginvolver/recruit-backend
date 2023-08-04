package com.yundingshuyuan.recruit.web.controller;

import com.yundingshuyuan.enums.RespStatusEnum;
import com.yundingshuyuan.recruit.dao.ApplicationPhotoMapper;
import com.yundingshuyuan.recruit.dao.RegisterInfoMapper;
import com.yundingshuyuan.recruit.domain.DirectionCountVO;
import com.yundingshuyuan.vo.BasicResultVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/direction")
@Tag(name = "统计各方向人数")
public class DirectionCountController {
    @Autowired
    private RegisterInfoMapper registerInfoMapper;
    @Autowired
    private ApplicationPhotoMapper applicationPhotoMapper;

    /**
     * 统计个方向报名人数、当日报名人数、男女比例
     * @return
     */
    @GetMapping("/count")
    @Operation(summary = "统计各方向人数")
    public BasicResultVO<List<DirectionCountVO>> getDirectionCounts() {
        List<DirectionCountVO> list = registerInfoMapper.countByDirection();
        return new BasicResultVO<>(RespStatusEnum.SUCCESS, list);
    }

    /**
     * 统计所有方向一共报名人数、提交申请书照片的总人数
     * @return
     */
    @GetMapping("/total")
    @Operation(summary = "统计总人数")
    public BasicResultVO<Map<String, Object>> getTotalCounts() {
        int registerInfoCount = registerInfoMapper.countRegisterInfo();
        int applicationPhotoCount = applicationPhotoMapper.countApplicationPhoto();
        Map<String, Object> counts = new HashMap<>();
        counts.put("registerInfoCount", registerInfoCount);
        counts.put("applicationPhotoCount", applicationPhotoCount);
        return new BasicResultVO<>(RespStatusEnum.SUCCESS, counts);
    }
}