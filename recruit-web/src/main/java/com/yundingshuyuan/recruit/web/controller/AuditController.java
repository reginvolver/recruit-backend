package com.yundingshuyuan.recruit.web.controller;

import com.yundingshuyuan.recruit.api.AuditService;
import com.yundingshuyuan.recruit.domain.vo.AuditResultVo;
import com.yundingshuyuan.recruit.web.annotation.PageQuery;
import com.yundingshuyuan.recruit.web.annotation.RecruitResult;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Tag(name = "超级管理员-审批审核接口")
@Slf4j
@RecruitResult
@RestController
@RequiredArgsConstructor
@RequestMapping("/audit")
public class AuditController {

    private final AuditService auditService;

    @PageQuery
    @GetMapping("/all")
    @Operation(summary = "获取所有结果")
    public List<AuditResultVo> getAllResult() {
        return auditService.getAllResult();
    }

    @PageQuery
    @GetMapping("/keyword/{keyword}")
    @Operation(summary = "聚合搜索，通过关键词")
    public List<AuditResultVo> getResultByKeyword(@PathVariable("keyword") String keyword, @RequestParam String search) {
        return auditService.getResultByKeyword(keyword, search);
    }

    @PageQuery
    @GetMapping("/state")
    @Operation(summary = "获取结果通过是否通过状态")
    public List<AuditResultVo> getPassedResultByPassState(@RequestParam boolean isPassed) {
        return auditService.getPassedResultByPassState(isPassed);
    }

    @GetMapping("/amount")
    @Operation(summary = "获取各状态人数统计")
    public Map<String, Long> getOutcomeAmount() {
        return auditService.getOutcomeAmount();
    }

    @GetMapping("/excel")
    @Operation(summary = "导出结果EXCEL表")
    public void getAllResultExportToExcel(@Autowired HttpServletResponse response) throws IOException {
        auditService.getAllResultExportToExcel(response);
    }

    @PostMapping("/sid/{studentId}")
    @Operation(summary = "通过学号更改通过结果")
    public int changePassedResultBySid(@PathVariable long studentId) {
        return auditService.changePassedResultBySid(studentId);
    }

    @PostMapping("/uid/{userId}")
    @Operation(summary = "通过uid号更改通过结果")
    public int changePassedResultByUid(@PathVariable long userId) {
        return auditService.changePassedResultByUid(userId);
    }

    @PostMapping("/allPassed")
    @Operation(summary = "一键通过")
    public int changeAllPassed() {
        return auditService.changeAllPassed();
    }
}
