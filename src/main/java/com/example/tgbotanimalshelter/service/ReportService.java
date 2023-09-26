package com.example.tgbotanimalshelter.service;

import com.example.tgbotanimalshelter.entity.Report;
import com.example.tgbotanimalshelter.exception.ReportNotFoundException;
import com.example.tgbotanimalshelter.repository.ReportRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReportService {

    private final ReportRepository repository;

    public ReportService(ReportRepository repository) {
        this.repository = repository;
    }

    public Report editReport(Report report) {
        return repository.save(report);
    }

    public Report findById(long id) {
        return repository.findById(id).orElseThrow(ReportNotFoundException::new);
    }

    @Transactional
    public Report update(Long id, Report report) {
        if (repository.existsById(id)) {
            report.setId(id);
            repository.save(report);
            return report;
        }
        throw new ReportNotFoundException();
    }
}
