package com.example.tgbotanimalshelter.command;

import com.example.tgbotanimalshelter.service.SendMessageService;
import com.example.tgbotanimalshelter.service.UserChatService;

public class RecordingContactCommandCat implements Command {
    private final SendMessageService sendMessageService;
    private final String text;
    private final UserChatService userChatService;

    public RecordingContactCommandCat(SendMessageService sendMessageService, String text, UserChatService userChatService) {
        this.sendMessageService = sendMessageService;
        this.text = text;
        this.userChatService = userChatService;
    }

    @Override
    public void execute(long chatId) {
        sendMessageService.sendMassage(chatId, text);
        userChatService.inviteWaitPhoneStatusCat(chatId);
    }

}
