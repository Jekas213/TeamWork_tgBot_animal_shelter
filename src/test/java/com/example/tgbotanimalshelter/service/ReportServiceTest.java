package com.example.tgbotanimalshelter.service;

import com.example.tgbotanimalshelter.entity.Report;
import com.example.tgbotanimalshelter.exception.ReportNotFoundException;
import com.example.tgbotanimalshelter.repository.ReportRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.example.tgbotanimalshelter.factory.ReportTestFactory.*;
import static org.assertj.core.api.Assertions.*;

class ReportServiceTest extends BaseServiceTest {

    @Autowired
    private ReportService reportService;

    @Autowired
    private ReportRepository reportRepository;

    @AfterEach
    void tearDown() {
        reportRepository.deleteAll();
    }

    @Test
    void editReportTest() {
        Report report = buildReport();

        Report editedReport = reportService.editReport(report);

        assertThat(editedReport)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(report);
    }

    @Test
    void findByIdTest() {
        Report report = reportRepository.save(buildReport());

        assertThat(reportService.findById(report.getId())).isEqualTo(report);
        assertThat(reportRepository.existsById(report.getId())).isTrue();
    }

    @Test
    void findByIdWhenReportNotFoundTest() {
        assertThatExceptionOfType(ReportNotFoundException.class)
                .isThrownBy(() -> reportService.findById(1L));
    }
    @Test
    void updateTest() {
        Report report = reportRepository.save(buildReport());

        assertThat(reportService.update(report.getId(), report)).isEqualTo(report);
    }

    @Test
    void updateWhenReportNotFoundTest() {
        Report report = buildReport();

        assertThatExceptionOfType(ReportNotFoundException.class)
                .isThrownBy(() -> reportService.update(report.getId(), report));
    }
}