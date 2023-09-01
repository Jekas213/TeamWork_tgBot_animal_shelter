package com.example.tgbotanimalshelter.command.dogsCommand;

import com.example.tgbotanimalshelter.command.CommandName;

import static com.example.tgbotanimalshelter.command.CommandName.*;

public enum EnumDogsInfo {

    REPORT_DOG("Отчёт о питомце"),
    INFO_DOG_SHELTERS("Вся информация о приюте для собак"),
    TAKE_DOGS("Информация о том как взять собаку из приюта"),
    INFO_START_DOGS(String.format("Выберите нужную Вам команду: \n"
                    + "%s Узнать информацию о приюте \n"
                    + "%s Как взять животное из приюта \n"
                    + "%s Прислать отчет о питомце \n"
                    + "%s Позвать волонтера",
            INFO_DOGS.getCommandName(), CommandName.TAKE_DOGS.getCommandName(), DOG_REPORT.getCommandName(), CALL_VOLUNTEER.getCommandName()));

    private final String enumTest;

    EnumDogsInfo(String enumTest) {
        this.enumTest = enumTest;
    }


    public String getCommandName() {
        return enumTest;
    }
}
