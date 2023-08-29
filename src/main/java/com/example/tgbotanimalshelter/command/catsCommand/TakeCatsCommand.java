package com.example.tgbotanimalshelter.command.catsCommand;

import com.example.tgbotanimalshelter.command.Command;
import com.example.tgbotanimalshelter.service.SendMassageService;
import com.pengrad.telegrambot.model.Update;

public class TakeCatsCommand implements Command {

    private final SendMassageService sendMassageService;

    public final static String TAKE_CATS = "Информация о том как взять кошку из приюта";

    public TakeCatsCommand(SendMassageService sendMassageService) {
        this.sendMassageService = sendMassageService;
    }

    @Override
    public void execute(Update update) {
        sendMassageService.sendMassage(update.message().chat().id(), TAKE_CATS);
    }
}
