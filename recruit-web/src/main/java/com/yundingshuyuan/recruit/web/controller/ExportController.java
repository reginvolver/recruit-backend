package com.yundingshuyuan.recruit.web.controller;

import com.yundingshuyuan.recruit.dao.CustomReservationMapper;
import com.yundingshuyuan.recruit.domain.ReservationEntity;
import com.yundingshuyuan.recruit.utils.ExcelExporter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/export")
public class ExportController {
    private final CustomReservationMapper customReservationMapper;

    public ExportController(CustomReservationMapper customReservationMapper) {
        this.customReservationMapper = customReservationMapper;
    }

    @GetMapping("/excel")
    public void exportToExcel(HttpServletResponse response) {
        List<ReservationEntity> reservationDataList = customReservationMapper.getReservationDataForExport();

        // 将数据转换为 Map 对象列表
        List<Map<String, Object>> dataList = convertToMapList(reservationDataList);

        // 设置响应头信息
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        response.setHeader("Content-disposition", "attachment;filename=data.xlsx");

        try {
            // 获取输出流
            OutputStream outputStream = response.getOutputStream();
            // 使用 ExcelExporter 类导出数据到 Excel
            ExcelExporter exporter = new ExcelExporter();
            exporter.exportToExcel(dataList, outputStream);
            outputStream.flush();
        } catch (IOException e) {
            log.error("Failed to export data to Excel: {}", e.getMessage());
        }
    }

    private List<Map<String, Object>> convertToMapList(List<ReservationEntity> reservationDataList) {
        List<Map<String, Object>> dataList = new ArrayList<>();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        for (ReservationEntity reservationEntity : reservationDataList) {
            Map<String, Object> dataMap = new HashMap<>();
            dataMap.put("编号", reservationEntity.getId());
            dataMap.put("面试时间", reservationEntity.getInterviewTime().format(formatter));
            dataMap.put("用户ID", reservationEntity.getUserId());
            dataMap.put("面试ID", reservationEntity.getInterviewId());
            dataMap.put("方向", reservationEntity.getDirection());
            dataMap.put("姓名", reservationEntity.getName());
            dataMap.put("QQ", reservationEntity.getQq());
            dataMap.put("性别", reservationEntity.getGender());
            dataMap.put("地点", reservationEntity.getLocation());

            dataList.add(dataMap);
        }

        return dataList;
    }
}