package com.yundingshuyuan.recruit.utils;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

public class ExcelExporter {
    public void exportToExcel(List<Map<String, Object>> dataList, OutputStream outputStream) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Reservation Data");

        // 创建表头
        Row headerRow = sheet.createRow(0);
        int columnCount = 0;
        for (String key : dataList.get(0).keySet()) {
            Cell cell = headerRow.createCell(columnCount++);
            cell.setCellValue(key);
        }

        // 填充数据
        int rowCount = 1;
        for (Map<String, Object> data : dataList) {
            Row dataRow = sheet.createRow(rowCount++);
            columnCount = 0;
            for (Object value : data.values()) {
                Cell cell = dataRow.createCell(columnCount++);
                if (value instanceof String) {
                    cell.setCellValue((String) value);
                } else if (value instanceof Integer) {
                    cell.setCellValue((Integer) value);
                } else if (value instanceof Double) {
                    cell.setCellValue((Double) value);
                } else {
                    cell.setCellValue(value.toString());
                }
            }
        }

        workbook.write(outputStream);
        workbook.close();
    }
}