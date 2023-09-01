package com.example.tgbotanimalshelter.command.catsCommand;

import com.example.tgbotanimalshelter.command.CommandName;

import static com.example.tgbotanimalshelter.command.CommandName.*;
import static com.example.tgbotanimalshelter.command.CommandName.CALL_VOLUNTEER;

public enum EnumCatsInfo {

    REPORT_CAT("Отчёт о питомце"),
    INFO_CAT_SHELTERS("Вся информация о приюте для кошек"),
    TAKE_CATS("Информация о том как взять кошку из приюта"),
    INFO_START_CATS(String.format("Выберите нужную Вам команду: \n"
                    + "%s Узнать информацию о приюте \n"
                    + "%s Как взять животное из приюта \n"
                    + "%s Прислать отчет о питомце \n"
                    + "%s Позвать волонтера",
            INFO_CATS.getCommandName(), CommandName.TAKE_CATS.getCommandName(), CAT_REPORT.getCommandName(), CALL_VOLUNTEER.getCommandName()));

    private final String enumTest;

    EnumCatsInfo(String enumTest) {
        this.enumTest = enumTest;
    }


    public String getCommandName() {
        return enumTest;
    }
}
