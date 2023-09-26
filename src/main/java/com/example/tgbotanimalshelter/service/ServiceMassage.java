package com.example.tgbotanimalshelter.service;


public enum ServiceMassage {
    INPUT_NAME("введите имя"),
    INCORRECT_NUMBER("неправильно набран номер"),
    RETURN_START("/start - Вкрнуться в главное меню"),
    INCORRECT_NAME("введите корректное имя"),
    WELL_BEING("Опишите общее самочувствие и привыкание к новому месту"),
    BEHAVIORS("Опишите изменение в поведении: отказ от старых привычек, приобретение новых"),
    PICTURE("Отправтье фото вашего питомца"),
    THANKS_REPORT("спасибо за отчёт"),
    SEND_PHOTO("вы ничего не отправили, пришлите фото");

    private final String commandName;

    ServiceMassage(String enumTest) {
        this.commandName = enumTest;
    }


    public String getCommandName() {
        return commandName;
    }
}
