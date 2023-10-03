package com.example.tgbotanimalshelter.command;

import com.example.tgbotanimalshelter.service.SendMessageService;

/**
 * The class is used to create a command object<br>
 * value = command descriptions
 */
public class InfoCommand implements Command {

    private final SendMessageService sendMessageService;
    private final String text;

    public InfoCommand(SendMessageService sendMessageService, String text) {
        this.sendMessageService = sendMessageService;
        this.text = text;
    }

    @Override
    public void execute(long chatId) {
        sendMessageService.sendMassage(chatId, text);
    }

}
