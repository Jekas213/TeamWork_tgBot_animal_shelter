package com.example.tgbotanimalshelter.command.dogsCommand;

import com.example.tgbotanimalshelter.command.Command;
import com.example.tgbotanimalshelter.service.SendMassageService;
import com.pengrad.telegrambot.model.Update;

public class TakeDogsCommand implements Command {

    private final SendMassageService sendMassageService;

    public final static String TAKE = "Информация о том как взять собаку из приюта";

    public TakeDogsCommand(SendMassageService sendMassageService) {
        this.sendMassageService = sendMassageService;
    }

    @Override
    public void execute(Update update) {
        sendMassageService.sendMassage(update.message().chat().id(), TAKE);
    }
}
