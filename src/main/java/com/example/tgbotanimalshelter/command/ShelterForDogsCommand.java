package com.example.tgbotanimalshelter.command;

import com.example.tgbotanimalshelter.service.SendMassageService;
import com.pengrad.telegrambot.model.Update;

import static com.example.tgbotanimalshelter.command.CommandName.*;

public class ShelterForDogsCommand implements Command {

    private final SendMassageService sendMassageService;

    public final static String DOGS = String.format("Выберите нужную Вам команду \n"
                    + "%s Узнать информацию о приюте \n"
                    + "%s Как взять животное из приюта \n"
                    + "%s Прислать отчет о питомце \n"
                    + "%s Позвать волонтера",
            INFO_DOGS.getCommandName(), TAKE_DOGS.getCommandName(), DOG_REPORT.getCommandName(), CALL_VOLUNTEER.getCommandName());

    public ShelterForDogsCommand(SendMassageService sendMassageService) {
        this.sendMassageService = sendMassageService;
    }

    @Override
    public void execute(Update update) {
        sendMassageService.sendMassage(update.message().chat().id(), DOGS);
    }
}
