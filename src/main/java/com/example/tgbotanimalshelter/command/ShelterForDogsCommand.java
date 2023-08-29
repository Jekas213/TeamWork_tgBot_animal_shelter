package com.example.tgbotanimalshelter.command;

import com.example.tgbotanimalshelter.service.SendMassageService;
import com.pengrad.telegrambot.model.Update;

import static com.example.tgbotanimalshelter.command.CommandName.*;

public class ShelterForDogsCommand extends ShelterCommand {
    public static final String DOGS = String.format(INFO,
            INFO_DOGS.getCommandName(), TAKE_DOGS.getCommandName(), DOG_REPORT.getCommandName(), CALL_VOLUNTEER.getCommandName());

    public ShelterForDogsCommand(SendMassageService sendMassageService) {
        super(sendMassageService);
    }

    @Override
    public void execute(Update update) {
        getSendMassageService().sendMassage(update.message().chat().id(), DOGS);
    }
}
