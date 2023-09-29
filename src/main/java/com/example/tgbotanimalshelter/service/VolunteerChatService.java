package com.example.tgbotanimalshelter.service;
import org.springframework.stereotype.Service;



@Service
public class VolunteerChatService {

    private final SendMessageService sendMessageService;
    private final UserChatService userChatService;

    public VolunteerChatService(SendMessageService sendMessageService, UserChatService userChatService) {
        this.sendMessageService = sendMessageService;
        this.userChatService = userChatService;
    }

    public void sendMessageToUser(long userId, long volunteerId, String text) {
        sendMessageService.sendMassage(userId, text);
        sendMessageService.sendMassage(volunteerId, text);
    }

    public void sendMessageByUser(String text){
        long volunteerId = 1860428928;
        sendMessageService.sendMassage(volunteerId,text);
    }

}
