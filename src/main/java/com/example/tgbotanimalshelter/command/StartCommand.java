package com.example.tgbotanimalshelter.command;

import com.example.tgbotanimalshelter.service.SendMassageService;
import com.pengrad.telegrambot.model.Update;


import static com.example.tgbotanimalshelter.command.CommandName.*;

public class StartCommand implements Command {

    private final SendMassageService sendMassageService;

    public StartCommand(SendMassageService sendMassageService) {
        this.sendMassageService = sendMassageService;
    }

    @Override
    public void execute(Update update) {
        sendMassageService.sendMassage(update.message().chat().id(), "выберите интерисующий Вас приют \n" + DOGS.getCommandName()
                + " - приют для собак \n" + CATS.getCommandName() + " - приют для кошек");
    }
}
