package com.example.tgbotanimalshelter.command.catsCommand;


import static com.example.tgbotanimalshelter.command.CommandName.*;

/**
 * The text of the description of the commands for the shelter for cats
 *
 * @see EnumCatsInfo#getCommandName()
 * @see java.lang.Enum
 */
public enum EnumCatsInfo {

    REPORT_CAT("Опишите рацион питомца"),
    INFO_CAT_SHELTERS(String.format("""
                    Выберите нужный пункт:\s
                    %s Описание приюта для кошек\s
                    %s Расписание работы, адресс, схема проезда\s
                    %s Оформление пропуска\s
                    %s Техника безопасности\s
                    %s Оставить контактные данные для связи\s
                    %s Позвать волонтера""",
            DESCRIPTION_CAT.getCommandName(),
            ADDRESS_CAT.getCommandName(),
            CONTACT_CAT.getCommandName(),
            SAFETY_CAT.getCommandName(),
            RECORDING_CAT.getCommandName(),
            CALL_VOLUNTEER.getCommandName())),
    DESCRIPTION_CAT_SHELTERS("описание приюта для кошек"),
    ADDRESS_CAT_SHELTERS("расписание работы приюта для кошек и адрес, схему проезда"),
    CONTACT_CAT_SHELTERS("контактные данные охраны для оформления пропуска на машину(приют для кошек)"),
    SAFETY_RECOMMENDATION_CAT("общие рекомендации о технике безопасности на территории приюта для кошек"),
    RECORDING_CONTACT_CAT("введите номер телефона (приют для кошек)"),
    TAKE_CATS(String.format("""
                    Выберите нужный пункт:\s
                    %s правила знакомства с животным до того, как забрать его из приюта(кошачий прию)\s
                    %s список документов, необходимых для того, чтобы взять животное из приюта(кошачий прию)\s
                    %s список рекомендаций по транспортировке животного(кошачий прию)\s
                    %s список рекомендаций по обустройству дома для котенка\s
                    %s список рекомендаций по обустройству дома для взрослого животного(кошачий прию)\s
                    %s список рекомендаций по обустройству дома для животного с ограниченными возможностями (зрение, передвижение)(кошачий прию)\s
                    %s список причин, почему могут отказать и не дать забрать кота/кошку из приюта\s
                    %s Оставить контактные данные для связи\s
                    %s Позвать волонтера""",
            DATING_RULES_CAT_COM.getCommandName(),
            DOCUMENTS_CAT_COM.getCommandName(),
            TRANSPORTATION_CAT_COM.getCommandName(),
            ARRANGING_KITTY_COM.getCommandName(),
            ARRANGING_CAT_COM.getCommandName(),
            DISABILITIES_CAT_COM.getCommandName(),
            REFUSALS_CAT_COM.getCommandName(),
            RECORDING_CAT.getCommandName(),
            CALL_VOLUNTEER.getCommandName()
    )),
    DATING_RULES_CAT("правила знакомства с животным(кошачий прию)"),
    DOCUMENTS_CAT("список документов(кошачий прию)"),
    TRANSPORTATION_CAT("список рекомендаций по транспортировке животного(кошачий прию)"),
    ARRANGING_KITTY("список рекомендаций по обустройству дома для котенка"),
    ARRANGING_CAT("список рекомендаций по обустройству дома для взрослсой кошки/кота"),
    DISABILITIES_CAT("список рекомендаций по обустройству дома для животного с ограниченными возможностями"),
    REFUSALS_CAT("почему могут отказать"),
    INFO_START_CATS(String.format("""
                    Выберите нужную Вам команду:\s
                    %s Узнать информацию о приюте\s
                    %s Как взять животное из приюта\s
                    %s Прислать отчет о питомце\s
                    %s Позвать волонтера""",
            INFO_CATS.getCommandName(),
            TAKE_CAT_COM.getCommandName(),
            CAT_REPORT.getCommandName(),
            CALL_VOLUNTEER.getCommandName()));

    private final String commandName;

    EnumCatsInfo(String enumTest) {
        this.commandName = enumTest;
    }


    public String getCommandName() {
        return commandName;
    }
}
