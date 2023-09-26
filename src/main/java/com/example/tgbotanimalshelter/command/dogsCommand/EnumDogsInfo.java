package com.example.tgbotanimalshelter.command.dogsCommand;


import static com.example.tgbotanimalshelter.command.CommandName.*;

/**
 * The text of the description of the commands for the shelter for dogs
 *
 * @see EnumDogsInfo#getCommandName()
 * @see java.lang.Enum
 */
public enum EnumDogsInfo {

    REPORT_DOG("Отчёт о питомце"),
    INFO_DOG_SHELTERS(String.format("""
                    Выберите нужный пункт:\s
                    %s Описание приюта для собак\s
                    %s Расписание работы, адресс, схема проезда\s
                    %s Оформление пропуска\s
                    %s Техника безопасности\s
                    %s Оставить контактные данные для связи\s
                    %s Позвать волонтера""",
            DESCRIPTION_DOG.getCommandName(),
            ADDRESS_DOG.getCommandName(),
            CONTACT_DOG.getCommandName(),
            SAFETY_DOG.getCommandName(),
            RECORDING_DOG.getCommandName(),
            CALL_VOLUNTEER.getCommandName())),
    DESCRIPTION_DOG_SHELTERS("описание приюта для собак"),
    ADDRESS_DOG_SHELTERS("расписание работы приюта для собак и адрес, схему проезда"),
    CONTACT_DOG_SHELTERS("контактные данные охраны для оформления пропуска на машину(приют для собак)"),
    SAFETY_RECOMMENDATION_DOG("общие рекомендации о технике безопасности на территории приюта для собак"),
    RECORDING_CONTACT_DOG("введите номер телефона (приют для собак)"),
    TAKE_DOGS(String.format("""
                    Выберите нужный пункт:\s
                    %s правила знакомства с животным до того, как забрать его из приюта(собачий прию)\s
                    %s список документов, необходимых для того, чтобы взять животное из приюта(собачий прию)\s
                    %s список рекомендаций по транспортировке животного(собачий прию)\s
                    %s список рекомендаций по обустройству дома для щенка\s
                    %s список рекомендаций по обустройству дома для взрослого животного(собачий прию)\s
                    %s список рекомендаций по обустройству дома для животного с ограниченными возможностями (зрение, передвижение)(собачий прию)\s
                    %s советы кинолога по первичному общению с собакой\s
                    %s список рекомендации по проверенным кинологам для дальнейшего обращения к ним\s
                    %s список причин, почему могут отказать и не дать забрать собаку из приюта\s
                    %s Оставить контактные данные для связи\s
                    %s Позвать волонтера""",
            DATING_RULES_DOG_COM.getCommandName(),
            DOCUMENTS_DOG_COM.getCommandName(),
            TRANSPORTATION_DOG_COM.getCommandName(),
            ARRANGING_PUPPY_COM.getCommandName(),
            ARRANGING_DOG_COM.getCommandName(),
            DISABILITIES_DOG_COM.getCommandName(),
            TIPS_DOG_HANDLER_COM.getCommandName(),
            LIST_DOG_HANDLER_COM.getCommandName(),
            REFUSALS_DOG_COM.getCommandName(),
            RECORDING_DOG.getCommandName(),
            CALL_VOLUNTEER.getCommandName()
    )),
    DATING_RULES_DOG("правила знакомства с животным(собачий прию)"),
    DOCUMENTS_DOG("список документов(собачий прию)"),
    TRANSPORTATION_DOG("список рекомендаций по транспортировке животного(собачий прию)"),
    ARRANGING_PUPPY("список рекомендаций по обустройству дома для щенка"),
    ARRANGING_DOG("список рекомендаций по обустройству дома для взрослсой собаки"),
    DISABILITIES_DOG("список рекомендаций по обустройству дома для животного с ограниченными возможностями"),
    TIPS_DOG_HANDLER("советы кинолога по первичному общению с собакой"),
    LIST_DOG_HANDLER("список кинологов"),
    REFUSALS_DOG("почему могут отказать"),
    INFO_START_DOGS(String.format("""
                    Выберите нужную Вам команду:\s
                    %s Узнать информацию о приюте\s
                    %s Как взять животное из приюта\s
                    %s Прислать отчет о питомце\s
                    %s Позвать волонтера""",
            INFO_DOGS.getCommandName(),
            TAKE_DOG_COM.getCommandName(),
            DOG_REPORT.getCommandName(),
            CALL_VOLUNTEER.getCommandName()));

    private final String commandName;

    EnumDogsInfo(String commandName) {
        this.commandName = commandName;
    }


    public String getCommandName() {
        return commandName;
    }
}
