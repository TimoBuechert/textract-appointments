package com.textract.appointments.controller;


import com.textract.appointments.service.AnalyzeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class TextractController {

    private final AnalyzeService analyzeService;

    public TextractController(@Autowired final AnalyzeService analyzeService) {
        this.analyzeService = analyzeService;
    }

    @PostMapping(value = "/analyze", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<Resource> analyzeData(@RequestParam(name = "file") final MultipartFile file) throws Exception {
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(analyzeService.analyzeDocument(file.getBytes()));
    }
}