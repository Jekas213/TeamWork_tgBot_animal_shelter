package com.example.tgbotanimalshelter.service;

import com.example.tgbotanimalshelter.entity.Report;
import com.example.tgbotanimalshelter.exception.ReportNotFoundException;
import com.example.tgbotanimalshelter.repository.ReportRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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


    @Transactional
    public List<Report> findByChatId(Long chatId) {
        return repository.findByChatId(chatId)
                .stream()
                .map(report -> Report.builder()
                        .id(report.getId())
                        .behaviors(report.getBehaviors())
                        .chatId(report.getChatId())
                        .date(report.getDate())
                        .diet(report.getDiet())
                        .wellBeing(report.getWellBeing())
                        .build())
                .toList();
    }

    @Transactional
    public byte[] findPictureByChatId(Long chatId, Long id) {
        return repository.findPictureByChatId(chatId, id)
                .orElseThrow(ReportNotFoundException::new);
    }
}
