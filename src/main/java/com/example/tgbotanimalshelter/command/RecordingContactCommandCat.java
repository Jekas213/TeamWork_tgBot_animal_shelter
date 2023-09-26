package com.example.tgbotanimalshelter.command;

import com.example.tgbotanimalshelter.service.SendMassageService;
import com.example.tgbotanimalshelter.service.UserChatService;

public class RecordingContactCommandCat implements Command {
    private final SendMassageService sendMassageService;
    private final String text;
    private final UserChatService userChatService;

    public RecordingContactCommandCat(SendMassageService sendMassageService, String text, UserChatService userChatService) {
        this.sendMassageService = sendMassageService;
        this.text = text;
        this.userChatService = userChatService;
    }

    @Override
    public void execute(long chatId) {
        sendMassageService.sendMassage(chatId, text);
        userChatService.inviteWaitPhoneStatusCat(chatId);
    }

}
