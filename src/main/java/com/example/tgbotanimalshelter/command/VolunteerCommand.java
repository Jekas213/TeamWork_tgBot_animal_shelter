package com.example.tgbotanimalshelter.command;

import com.example.tgbotanimalshelter.entity.UserChat;
import com.example.tgbotanimalshelter.service.SendMessageService;
import com.example.tgbotanimalshelter.service.ServiceMessage;
import com.example.tgbotanimalshelter.service.UserChatService;
import com.example.tgbotanimalshelter.service.VolunteerService;

public class VolunteerCommand implements Command {
    private final SendMessageService sendMessageService;
    private final String text;
    private final UserChatService userChatService;
    private final VolunteerService volunteerService;

    public VolunteerCommand(SendMessageService sendMessageService, String text, UserChatService userChatService, VolunteerService volunteerService) {
        this.sendMessageService = sendMessageService;
        this.text = text;
        this.userChatService = userChatService;
        this.volunteerService = volunteerService;
    }

    @Override
    public void execute(long chatId) {
        long volunteerId = volunteerService.getRandomVolunteerId();
        UserChat userChat = userChatService.findById(chatId);
        userChat.setVolunteerId(volunteerId);
        userChatService.update(chatId, userChat);
        sendMessageService.sendMassage(chatId, text);
        sendMessageService.sendMassage(volunteerId, ServiceMessage.INFO_USER.getCommandName() + " чат Id: " + chatId);
    }
}
