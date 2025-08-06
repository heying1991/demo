package com.example.demo.service;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

@Service
public class LargeExcelService {

    // 流式写入 Excel（关键：使用 SXSSFWorkbook）
    public void writeLargeExcel(OutputStream outputStream) throws IOException {
        // 设置内存中保留的行数（超过部分写入磁盘临时文件）
        int rowAccessWindowSize = 100; // 根据内存调整（通常100-500行）
        try (SXSSFWorkbook workbook = new SXSSFWorkbook(rowAccessWindowSize)) {
            Sheet sheet = workbook.createSheet("大数据报表");

            // 1. 写入表头
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("ID");
            headerRow.createCell(1).setCellValue("名称");
            // ... 其他列

            // 2. 分页查询数据并写入（伪代码）
            int pageSize = 1000; // 每页数据量
            int currentPage = 0;
            int rowIndex = 1; // 数据从第2行开始
            int count = 10000 * 100;
            while (count-- > 0) {
                // 分页获取数据（避免全量加载到内存）


                // 流式写入当前分页数据

                Row row = sheet.createRow(rowIndex++);
                int tt=0;
                row.createCell(tt++).setCellValue("name");
                row.createCell(tt++).setCellValue("year");
                row.createCell(tt++).setCellValue("old");
                row.createCell(tt++).setCellValue("baby");
                row.createCell(tt++).setCellValue("old");
                row.createCell(tt++).setCellValue("name");
                row.createCell(tt++).setCellValue("year");
                row.createCell(tt++).setCellValue("old");
                row.createCell(tt++).setCellValue("baby");
                row.createCell(tt++).setCellValue("old");
                row.createCell(tt++).setCellValue("name");
                row.createCell(tt++).setCellValue("year");
                row.createCell(tt++).setCellValue("old");
                row.createCell(tt++).setCellValue("baby");
                row.createCell(tt++).setCellValue("old");
                row.createCell(tt++).setCellValue("name");
                row.createCell(tt++).setCellValue("year");
                row.createCell(tt++).setCellValue("old");
                row.createCell(tt++).setCellValue("baby");
                row.createCell(tt++).setCellValue("old");
                // ... 其他列

                // 手动刷新写入磁盘（释放内存）
                if (rowIndex % 1000 == 0) {
                    ((SXSSFSheet) sheet).flushRows(100); // 保留最近100行在内存
                }

                currentPage++;
            }

            // 3. 最终写入输出流
            workbook.write(outputStream);
            workbook.dispose(); // 删除临时文件
        }
    }
}