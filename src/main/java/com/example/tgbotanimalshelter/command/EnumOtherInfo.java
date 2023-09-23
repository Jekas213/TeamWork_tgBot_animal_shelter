package com.example.tgbotanimalshelter.command;

import static com.example.tgbotanimalshelter.command.CommandName.*;

public enum EnumOtherInfo {

    VOLUNTEER("связаться в волонтером"),
    UNKNOWN("такой команды нет, введите команду из предложенных Вам, либо начните заного введя команду " + START.getCommandName()),
    START_FIRST("выберите интерисующий Вас приют \n" + DOGS.getCommandName()
            + " - приют для собак \n" + CATS.getCommandName() + " - приют для кошек");

    private final String commandName;

    EnumOtherInfo(String enumTest) {
        this.commandName = enumTest;
    }


    public String getCommandName() {
        return commandName;
    }
}
