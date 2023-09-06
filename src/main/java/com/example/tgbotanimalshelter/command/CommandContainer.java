package com.example.tgbotanimalshelter.command;

import com.example.tgbotanimalshelter.command.catsCommand.EnumCatsInfo;
import com.example.tgbotanimalshelter.command.dogsCommand.EnumDogsInfo;
import com.example.tgbotanimalshelter.service.SendMassageService;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

import static com.example.tgbotanimalshelter.command.CommandName.*;

@Component
public class CommandContainer {

    private final Map<String, Command> commandMap;
    private final Command unknownCommand;

    public CommandContainer(SendMassageService sendMassageService) {
        commandMap = new HashMap<>();
        commandMap.put(START.getCommandName(), new Info(sendMassageService,EnumOtherInfo.START_FIRST.getCommandName()));
        commandMap.put(DOGS.getCommandName(), new Info(sendMassageService, EnumDogsInfo.INFO_START_DOGS.getCommandName()));
        commandMap.put(CATS.getCommandName(), new Info(sendMassageService, EnumCatsInfo.INFO_START_CATS.getCommandName()));
        commandMap.put(INFO_DOGS.getCommandName(), new Info(sendMassageService,EnumDogsInfo.INFO_DOG_SHELTERS.getCommandName()));
        commandMap.put(INFO_CATS.getCommandName(), new Info(sendMassageService, EnumCatsInfo.INFO_CAT_SHELTERS.getCommandName()));
        commandMap.put(TAKE_DOGS.getCommandName(), new Info(sendMassageService, EnumDogsInfo.TAKE_DOGS.getCommandName()));
        commandMap.put(TAKE_CATS.getCommandName(), new Info(sendMassageService,EnumCatsInfo.TAKE_CATS.getCommandName()));
        commandMap.put(DOG_REPORT.getCommandName(), new Info(sendMassageService, EnumDogsInfo.REPORT_DOG.getCommandName()));
        commandMap.put(CAT_REPORT.getCommandName(), new Info(sendMassageService,EnumCatsInfo.REPORT_CAT.getCommandName()));
        commandMap.put(CALL_VOLUNTEER.getCommandName(), new Info(sendMassageService,EnumOtherInfo.VOLUNTEER.getCommandName()));


        unknownCommand = new Info(sendMassageService,EnumOtherInfo.UNKNOWN.getCommandName());
    }

    public Command command(String commandName) {
        return commandMap.getOrDefault(commandName, unknownCommand);
    }
}
