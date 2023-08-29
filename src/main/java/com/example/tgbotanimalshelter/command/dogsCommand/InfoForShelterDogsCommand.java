package com.example.tgbotanimalshelter.command.dogsCommand;

import com.example.tgbotanimalshelter.command.Command;
import com.example.tgbotanimalshelter.service.SendMassageService;
import com.pengrad.telegrambot.model.Update;

public class InfoForShelterDogsCommand implements Command {

    private final SendMassageService sendMassageService;

    public final static String INFO = "Информация о приюте для собак";

    public InfoForShelterDogsCommand(SendMassageService sendMassageService) {
        this.sendMassageService = sendMassageService;
    }

    @Override
    public void execute(Update update) {
        sendMassageService.sendMassage(update.message().chat().id(), INFO);
    }
}
