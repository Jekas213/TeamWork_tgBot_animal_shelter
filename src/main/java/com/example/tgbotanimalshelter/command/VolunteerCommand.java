package com.example.tgbotanimalshelter.command;

import com.example.tgbotanimalshelter.command.Command;
import com.example.tgbotanimalshelter.service.SendMessageService;
import com.example.tgbotanimalshelter.service.ServiceMessage;
import com.example.tgbotanimalshelter.service.UserChatService;

public class VolunteerCommand implements Command {
    private final SendMessageService sendMessageService;
    private final String text;
    private final UserChatService userChatService;

    public VolunteerCommand(SendMessageService sendMessageService, String text, UserChatService userChatService) {
        this.sendMessageService = sendMessageService;
        this.text = text;
        this.userChatService = userChatService;
    }

    @Override
    public void execute(long chatId) {
        long volunteerId = 1860428928;
        sendMessageService.sendMassage(chatId, text);
        sendMessageService.sendMassage(volunteerId, ServiceMessage.INFO_USER.getCommandName() + " чат Id: " + chatId);
    }
}
