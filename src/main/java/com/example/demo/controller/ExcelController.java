package com.example.demo.controller;

import com.example.demo.service.LargeExcelService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;


import java.io.OutputStream;

@RestController
public class ExcelController {

    @Autowired
    private LargeExcelService excelService;

    @PostMapping("/download")
    public ResponseEntity<StreamingResponseBody> downloadLargeExcel(HttpServletResponse response) {
        // 设置响应头
        String fileName = "large-report.xlsx";
        response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"");

        // 使用 StreamingResponseBody 实现流式响应
        StreamingResponseBody stream = out -> {
            excelService.writeLargeExcel(out);
            out.flush();
        };

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE)
                .body(stream);
    }
}
