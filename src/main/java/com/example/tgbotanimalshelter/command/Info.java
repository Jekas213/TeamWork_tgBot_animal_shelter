package com.example.tgbotanimalshelter.command;

import com.example.tgbotanimalshelter.service.SendMassageService;
import com.pengrad.telegrambot.model.Update;

public class Info implements Command {

    private final SendMassageService sendMassageService;
    private final String value;

    public Info(SendMassageService sendMassageService, String value) {
        this.sendMassageService = sendMassageService;
        this.value = value;
    }

    @Override
    public void execute(Update update) {
        sendMassageService.sendMassage(update.message().chat().id(), value);
    }
}
