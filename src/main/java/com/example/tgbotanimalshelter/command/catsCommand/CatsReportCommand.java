package com.example.tgbotanimalshelter.command.catsCommand;

import com.example.tgbotanimalshelter.command.Command;
import com.example.tgbotanimalshelter.service.SendMassageService;
import com.pengrad.telegrambot.model.Update;

public class CatsReportCommand implements Command {

    private final SendMassageService sendMassageService;

    public final static String REPORT = "Отчёт о питомце";

    public CatsReportCommand(SendMassageService sendMassageService) {
        this.sendMassageService = sendMassageService;
    }

    @Override
    public void execute(Update update) {
        sendMassageService.sendMassage(update.message().chat().id(), REPORT);
    }
}
