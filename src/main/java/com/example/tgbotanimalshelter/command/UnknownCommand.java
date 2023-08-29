package com.example.tgbotanimalshelter.command;

import com.example.tgbotanimalshelter.service.SendMassageService;
import com.pengrad.telegrambot.model.Update;

import static com.example.tgbotanimalshelter.command.CommandName.*;

public class UnknownCommand implements Command {

    private final SendMassageService sendMassageService;

    public static final String UNKNOWN = "такой команды нет, введите команду из предложенных Вам, либо начните заного введя команду " + START.getCommandName();

    public UnknownCommand(SendMassageService sendMassageService) {
        this.sendMassageService = sendMassageService;
    }

    @Override
    public void execute(Update update) {
        sendMassageService.sendMassage(update.message().chat().id(), UNKNOWN);
    }
}
