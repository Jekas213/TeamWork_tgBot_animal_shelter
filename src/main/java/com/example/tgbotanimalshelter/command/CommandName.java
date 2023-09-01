package com.example.tgbotanimalshelter.command;

public enum CommandName {
    START("/start"),
    DOGS("/dogs"),
    CATS("/cats"),
    INFO_DOGS("/infoDogs"),
    INFO_CATS("/infoCats"),
    TAKE_DOGS("/howTakeDog"),
    TAKE_CATS("/howTakeCat"),
    DOG_REPORT("/sendReportDogs"),
    CAT_REPORT("/sendReportCats"),
    CALL_VOLUNTEER("/callVolunteer");


    private final String commandName;

    CommandName(String commandName) {
        this.commandName = commandName;
    }

    public String getCommandName() {
        return commandName;
    }
}
