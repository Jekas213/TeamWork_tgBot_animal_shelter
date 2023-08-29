package com.example.tgbotanimalshelter.command;

import com.example.tgbotanimalshelter.service.SendMassageService;
import com.pengrad.telegrambot.model.Update;

import static com.example.tgbotanimalshelter.command.CommandName.*;

public class ShelterForCatsCommand extends ShelterCommand {
    public static final String CATS = String.format(INFO,
            INFO_CATS.getCommandName(), TAKE_CATS.getCommandName(), CAT_REPORT.getCommandName(), CALL_VOLUNTEER.getCommandName());

    public ShelterForCatsCommand(SendMassageService sendMassageService) {
        super(sendMassageService);
    }

    @Override
    public void execute(Update update) {
        getSendMassageService().sendMassage(update.message().chat().id(), CATS);
    }
}
