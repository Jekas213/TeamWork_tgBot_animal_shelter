package com.example.tgbotanimalshelter.command;

import com.example.tgbotanimalshelter.service.SendMassageService;

public abstract class ShelterCommand implements Command {
    private final SendMassageService sendMassageService;
    public static final String INFO = """
            Выберите нужную Вам команду:
            - %s Узнать информацию о приюте
            - %s Как взять животное из приюта
            - %s Прислать отчет о питомце
            - %s Позвать волонтера
            """;

    protected ShelterCommand(SendMassageService sendMassageService) {
        this.sendMassageService = sendMassageService;
    }

    public SendMassageService getSendMassageService() {
        return sendMassageService;
    }
}
