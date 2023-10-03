package com.example.tgbotanimalshelter.service;

import com.example.tgbotanimalshelter.exception.VolunteerNotFoundException;
import com.example.tgbotanimalshelter.repository.VolunteerRepository;
import org.springframework.stereotype.Service;


@Service
public class VolunteerChatService {

    private final SendMessageService sendMessageService;
    private final VolunteerRepository volunteerRepository;
    private final UserChatService userChatService;

    public VolunteerChatService(SendMessageService sendMessageService, VolunteerRepository volunteerRepository, UserChatService userChatService) {
        this.sendMessageService = sendMessageService;
        this.volunteerRepository = volunteerRepository;
        this.userChatService = userChatService;
    }

    public void sendMessageToUser(long userId, long volunteerId, String text) {
        sendMessageService.sendMassage(userId, text);
        sendMessageService.sendMassage(volunteerId, text);
    }

    public void sendMessageByUser(long chatId, String text) {
        long volunteerId = userChatService.getVolunteerIdByUserChatId(chatId);
        sendMessageService.sendMassage(volunteerId, text);
    }

}
