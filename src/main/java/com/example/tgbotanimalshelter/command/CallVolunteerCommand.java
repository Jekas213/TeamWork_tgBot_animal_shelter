package com.example.tgbotanimalshelter.command;

import com.example.tgbotanimalshelter.service.SendMassageService;
import com.pengrad.telegrambot.model.Update;

public class CallVolunteerCommand implements Command {

    private final SendMassageService sendMassageService;

    public static final String VOLUNTEER = "связаться в волонтером";

    public CallVolunteerCommand(SendMassageService sendMassageService) {
        this.sendMassageService = sendMassageService;
    }

    @Override
    public void execute(Update update) {
        sendMassageService.sendMassage(update.message().chat().id(), VOLUNTEER);
    }
}
