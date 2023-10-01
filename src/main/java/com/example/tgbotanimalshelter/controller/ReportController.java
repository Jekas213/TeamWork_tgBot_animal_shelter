package com.example.tgbotanimalshelter.controller;

import com.example.tgbotanimalshelter.entity.Report;
import com.example.tgbotanimalshelter.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/report")
@RequiredArgsConstructor
public class ReportController {


    private final ReportService reportService;
    @GetMapping("/{id}")
    public ResponseEntity<Report> findById(@PathVariable Long id) {
        return ResponseEntity.ok(reportService.findById(id));
    }
}
