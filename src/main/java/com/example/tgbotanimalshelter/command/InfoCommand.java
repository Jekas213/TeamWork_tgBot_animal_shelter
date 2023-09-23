package com.example.tgbotanimalshelter.command;

import com.example.tgbotanimalshelter.service.SendMassageService;

/**
 * The class is used to create a command object<br>
 * value = command descriptions
 */
public class InfoCommand implements Command {

    private final SendMassageService sendMassageService;
    private final String text;

    public InfoCommand(SendMassageService sendMassageService, String text) {
        this.sendMassageService = sendMassageService;
        this.text = text;
    }

    @Override
    public void execute(long chatId) {
        sendMassageService.sendMassage(chatId, text);
    }

}
