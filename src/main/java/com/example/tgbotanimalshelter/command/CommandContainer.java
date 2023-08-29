package com.example.tgbotanimalshelter.command;

import com.example.tgbotanimalshelter.command.catsCommand.CatsReportCommand;
import com.example.tgbotanimalshelter.command.catsCommand.InfoForShelterCatsCommand;
import com.example.tgbotanimalshelter.command.catsCommand.TakeCatsCommand;
import com.example.tgbotanimalshelter.command.dogsCommand.DogsReportCommand;
import com.example.tgbotanimalshelter.command.dogsCommand.InfoForShelterDogsCommand;
import com.example.tgbotanimalshelter.command.dogsCommand.TakeDogsCommand;
import com.example.tgbotanimalshelter.service.SendMassageService;

import java.util.Map;

import static com.example.tgbotanimalshelter.command.CommandName.*;

public class CommandContainer {

    private final Map<String, Command> commandMap;
    private final Command unknownCommand;

    public CommandContainer(SendMassageService sendMassageService) {
        commandMap = Map.of(
                DOGS.getCommandName(), new ShelterForDogsCommand(sendMassageService),
                CATS.getCommandName(), new ShelterForCatsCommand(sendMassageService),
                INFO_DOGS.getCommandName(), new InfoForShelterDogsCommand(sendMassageService),
                INFO_CATS.getCommandName(), new InfoForShelterCatsCommand(sendMassageService),
                TAKE_DOGS.getCommandName(), new TakeDogsCommand(sendMassageService),
                TAKE_CATS.getCommandName(), new TakeCatsCommand(sendMassageService),
                DOG_REPORT.getCommandName(), new DogsReportCommand(sendMassageService),
                CAT_REPORT.getCommandName(), new CatsReportCommand(sendMassageService),
                CALL_VOLUNTEER.getCommandName(), new CallVolunteerCommand(sendMassageService),
                START.getCommandName(), new StartCommand(sendMassageService)
        );

        unknownCommand = new UnknownCommand(sendMassageService);
    }

    public Command command(String commandName) {
        return commandMap.getOrDefault(commandName, unknownCommand);
    }
}
