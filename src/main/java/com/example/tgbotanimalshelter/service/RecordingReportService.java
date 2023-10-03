package com.example.tgbotanimalshelter.service;

import com.example.tgbotanimalshelter.entity.Report;
import com.example.tgbotanimalshelter.entity.UserChat;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.PhotoSize;
import com.pengrad.telegrambot.request.GetFile;
import com.pengrad.telegrambot.response.GetFileResponse;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;

import static com.example.tgbotanimalshelter.entity.StatusUserChat.*;
import static com.example.tgbotanimalshelter.service.ServiceMessage.*;

@Service
public class RecordingReportService {

    private final ReportService reportService;
    private final UserChatService userChatService;
    private final SendMessageService sendMessageService;
    private final TelegramBot telegramBot;

    public RecordingReportService(ReportService reportService, UserChatService userChatService, SendMessageService sendMessageService, TelegramBot telegramBot) {
        this.reportService = reportService;
        this.userChatService = userChatService;
        this.sendMessageService = sendMessageService;
        this.telegramBot = telegramBot;
    }

    public void recordingDiet(long chatId, String text) {
        Date date = new Date();
        Report report = new Report();
        report.setChatId(chatId);
        report.setDate(date);
        report.setDiet(text);
        reportService.editReport(report);
        long reportId = report.getId();
        UserChat userChat = userChatService.findById(chatId);
        userChat.setStatusUserChat(WAIT_FOR_WELL_BEING);
        userChat.setReportId(reportId);
        userChatService.update(chatId, userChat);
        sendMessageService.sendMassage(chatId, WELL_BEING.getCommandName());
    }

    public void recordingWellBeing(long chatId, String text) {
        UserChat userChat = userChatService.findById(chatId);
        long reportId = userChat.getReportId();
        Report report = reportService.findById(reportId);
        report.setWellBeing(text);
        reportService.update(reportId, report);
        userChat.setStatusUserChat(WAIT_FOR_BEHAVIORS);
        userChatService.update(chatId, userChat);
        sendMessageService.sendMassage(chatId, BEHAVIORS.getCommandName());
    }

    public void recordingBehaviors(long chatId, String text) {
        UserChat userChat = userChatService.findById(chatId);
        long reportId = userChat.getReportId();
        Report report = reportService.findById(reportId);
        report.setBehaviors(text);
        reportService.update(reportId, report);
        userChat.setStatusUserChat(WAIT_FOR_PICTURE);
        userChatService.update(chatId, userChat);
        sendMessageService.sendMassage(chatId, PICTURE.getCommandName());
    }

    public void recordingPhoto(long chatId, PhotoSize[] photoSizes) {
        if (photoSizes != null) {
            long reportId = userChatService.findById(chatId).getReportId();
            Report report = reportService.findById(reportId);
            PhotoSize photoSize = photoSizes[photoSizes.length - 1];
            GetFileResponse getFileResponse = telegramBot.execute(new GetFile(photoSize.fileId()));
            if (getFileResponse.isOk()) {
                try {
                    byte[] data = telegramBot.getFileContent(getFileResponse.file());
                    report.setPicture(data);
                    reportService.update(reportId, report);
                    UserChat userChat = userChatService.findById(chatId);
                    userChat.setStatusUserChat(BASIC_STATUS);
                    userChatService.update(chatId, userChat);
                    sendMessageService.sendMassage(chatId, THANKS_REPORT.getCommandName());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

}
