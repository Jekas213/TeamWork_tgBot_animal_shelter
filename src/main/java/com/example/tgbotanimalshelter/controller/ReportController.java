package com.example.tgbotanimalshelter.controller;

import com.example.tgbotanimalshelter.entity.Report;
import com.example.tgbotanimalshelter.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.MediaType.*;

@RestController
@RequestMapping("/report")
@RequiredArgsConstructor
public class ReportController {


    private final ReportService reportService;

    @GetMapping("/{chatId}")
    public ResponseEntity<List<Report>> findById(@PathVariable Long chatId) {
        return ResponseEntity.ok(reportService.findByChatId(chatId));
    }


    @GetMapping("/{id}/{chatId}/picture")
    public ResponseEntity<byte[]> findPicture(@PathVariable Long chatId,
                                              @PathVariable Long id) {
        byte[] picture = reportService.findPictureByChatId(chatId, id);
        return ResponseEntity.ok().contentType(parseMediaType(IMAGE_JPEG_VALUE))
                .contentLength(picture.length)
                .body(picture);

    }
}
