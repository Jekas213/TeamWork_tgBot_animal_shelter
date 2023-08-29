package com.example.tgbotanimalshelter.command.catsCommand;

import com.example.tgbotanimalshelter.command.Command;
import com.example.tgbotanimalshelter.service.SendMassageService;
import com.pengrad.telegrambot.model.Update;

public class InfoForShelterCatsCommand implements Command {

    private final SendMassageService sendMassageService;

    public final static String INFO = "Вся информация о приюте для кошек";

    public InfoForShelterCatsCommand(SendMassageService sendMassageService) {
        this.sendMassageService = sendMassageService;
    }

    @Override
    public void execute(Update update) {
        sendMassageService.sendMassage(update.message().chat().id(), INFO);
    }
}
