package com.example.tgbotanimalshelter.command;

import com.example.tgbotanimalshelter.service.*;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

import static com.example.tgbotanimalshelter.command.CommandName.*;
import static com.example.tgbotanimalshelter.command.CommandName.DISABILITIES_CAT_COM;
import static com.example.tgbotanimalshelter.command.catsCommand.EnumCatsInfo.*;
import static com.example.tgbotanimalshelter.command.dogsCommand.EnumDogsInfo.*;

@Component
public class CommandContainer {

    private final Map<String, Command> commandMap;
    private final Command unknownCommand;

    public CommandContainer(SendMessageService sendMessageService, UserChatService userChatService, VolunteerService volunteerService, DogParentService dogParentService,
                            CatParentService catParentService) {
        commandMap = new HashMap<>();
        commandMap.put(START.getCommandName(), new InfoCommand(sendMessageService, EnumOtherInfo.START_FIRST.getCommandName()));
        commandMap.put(DOGS.getCommandName(), new InfoCommand(sendMessageService, INFO_START_DOGS.getCommandName()));
        commandMap.put(CATS.getCommandName(), new InfoCommand(sendMessageService, INFO_START_CATS.getCommandName()));
        commandMap.put(INFO_DOGS.getCommandName(), new InfoCommand(sendMessageService, INFO_DOG_SHELTERS.getCommandName()));
        commandMap.put(INFO_CATS.getCommandName(), new InfoCommand(sendMessageService, INFO_CAT_SHELTERS.getCommandName()));
        commandMap.put(TAKE_DOG_COM.getCommandName(), new InfoCommand(sendMessageService, TAKE_DOGS.getCommandName()));
        commandMap.put(TAKE_CAT_COM.getCommandName(), new InfoCommand(sendMessageService, TAKE_CATS.getCommandName()));
        commandMap.put(DOG_REPORT.getCommandName(), new RecordingReportCommand(sendMessageService, REPORT_DOG.getCommandName(), userChatService, dogParentService, catParentService));
        commandMap.put(CAT_REPORT.getCommandName(), new RecordingReportCommand(sendMessageService, REPORT_CAT.getCommandName(), userChatService, dogParentService, catParentService));
        commandMap.put(CALL_VOLUNTEER.getCommandName(), new VolunteerCommand(sendMessageService, EnumOtherInfo.VOLUNTEER.getCommandName(), userChatService, volunteerService));
        commandMap.put(DESCRIPTION_CAT.getCommandName(), new InfoCommand(sendMessageService, DESCRIPTION_CAT_SHELTERS.getCommandName()));
        commandMap.put(ADDRESS_CAT.getCommandName(), new InfoCommand(sendMessageService, ADDRESS_CAT_SHELTERS.getCommandName()));
        commandMap.put(CONTACT_CAT.getCommandName(), new InfoCommand(sendMessageService, CONTACT_CAT_SHELTERS.getCommandName()));
        commandMap.put(SAFETY_CAT.getCommandName(), new InfoCommand(sendMessageService, SAFETY_RECOMMENDATION_CAT.getCommandName()));
        commandMap.put(RECORDING_CAT.getCommandName(), new RecordingContactCommandCat(sendMessageService, RECORDING_CONTACT_CAT.getCommandName(), userChatService));
        commandMap.put(DESCRIPTION_DOG.getCommandName(), new InfoCommand(sendMessageService, DESCRIPTION_DOG_SHELTERS.getCommandName()));
        commandMap.put(ADDRESS_DOG.getCommandName(), new InfoCommand(sendMessageService, ADDRESS_DOG_SHELTERS.getCommandName()));
        commandMap.put(CONTACT_DOG.getCommandName(), new InfoCommand(sendMessageService, CONTACT_DOG_SHELTERS.getCommandName()));
        commandMap.put(SAFETY_DOG.getCommandName(), new InfoCommand(sendMessageService, SAFETY_RECOMMENDATION_DOG.getCommandName()));
        commandMap.put(RECORDING_DOG.getCommandName(), new RecordingContactCommandDog(sendMessageService, RECORDING_CONTACT_DOG.getCommandName(), userChatService));
        commandMap.put(DATING_RULES_DOG_COM.getCommandName(), new InfoCommand(sendMessageService, DATING_RULES_DOG.getCommandName()));
        commandMap.put(DOCUMENTS_DOG_COM.getCommandName(), new InfoCommand(sendMessageService, DOCUMENTS_DOG.getCommandName()));
        commandMap.put(TRANSPORTATION_DOG_COM.getCommandName(), new InfoCommand(sendMessageService, TRANSPORTATION_DOG.getCommandName()));
        commandMap.put(ARRANGING_PUPPY_COM.getCommandName(), new InfoCommand(sendMessageService, ARRANGING_PUPPY.getCommandName()));
        commandMap.put(ARRANGING_DOG_COM.getCommandName(), new InfoCommand(sendMessageService, ARRANGING_DOG.getCommandName()));
        commandMap.put(DISABILITIES_DOG_COM.getCommandName(), new InfoCommand(sendMessageService, DISABILITIES_DOG.getCommandName()));
        commandMap.put(TIPS_DOG_HANDLER_COM.getCommandName(), new InfoCommand(sendMessageService, TIPS_DOG_HANDLER.getCommandName()));
        commandMap.put(LIST_DOG_HANDLER_COM.getCommandName(), new InfoCommand(sendMessageService, LIST_DOG_HANDLER.getCommandName()));
        commandMap.put(REFUSALS_DOG_COM.getCommandName(), new InfoCommand(sendMessageService, REFUSALS_DOG.getCommandName()));
        commandMap.put(DATING_RULES_CAT_COM.getCommandName(), new InfoCommand(sendMessageService, DATING_RULES_CAT.getCommandName()));
        commandMap.put(DOCUMENTS_CAT_COM.getCommandName(), new InfoCommand(sendMessageService, DOCUMENTS_CAT.getCommandName()));
        commandMap.put(TRANSPORTATION_CAT_COM.getCommandName(), new InfoCommand(sendMessageService, TRANSPORTATION_CAT.getCommandName()));
        commandMap.put(ARRANGING_KITTY_COM.getCommandName(), new InfoCommand(sendMessageService, ARRANGING_KITTY.getCommandName()));
        commandMap.put(ARRANGING_CAT_COM.getCommandName(), new InfoCommand(sendMessageService, ARRANGING_CAT.getCommandName()));
        commandMap.put(DISABILITIES_CAT_COM.getCommandName(), new InfoCommand(sendMessageService, DISABILITIES_CAT.getCommandName()));
        commandMap.put(REFUSALS_CAT_COM.getCommandName(), new InfoCommand(sendMessageService, REFUSALS_CAT.getCommandName()));

        unknownCommand = new InfoCommand(sendMessageService, EnumOtherInfo.UNKNOWN.getCommandName());
    }

    public Command command(String commandName) {
        return commandMap.getOrDefault(commandName, unknownCommand);
    }
}
