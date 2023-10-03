package com.example.tgbotanimalshelter.service;


public enum ServiceMessage {
    INPUT_NAME("введите имя"),
    INCORRECT_NUMBER("неправильно набран номер"),
    RETURN_START("/start - Вернуться в главное меню"),
    INCORRECT_NAME("введите корректное имя"),
    WELL_BEING("Опишите общее самочувствие и привыкание к новому месту"),
    BEHAVIORS("Опишите изменение в поведении: отказ от старых привычек, приобретение новых"),
    PICTURE("Отправтье фото вашего питомца"),
    THANKS_REPORT("спасибо за отчёт"),
    SEND_PHOTO("вы ничего не отправили, пришлите фото"),
    BAD_REPORT("Дорогой усыновитель, мы заметили, что ты заполняешь отчет не так подробно, как необходимо. " +
            "Пожалуйста, подойди ответственнее к этому занятию. " +
            "В противном случае волонтеры приюта будут обязаны самолично проверять условия содержания животного"),
    INFO_USER("с вами хочет связаться пользоватьель"),
    CONTACT_INFO("пользователь оставил контактные данные для связи");

    private final String commandName;

    ServiceMessage(String enumTest) {
        this.commandName = enumTest;
    }


    public String getCommandName() {
        return commandName;
    }
}
