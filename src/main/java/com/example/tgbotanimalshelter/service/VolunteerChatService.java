package com.example.tgbotanimalshelter.service;

import com.example.tgbotanimalshelter.exception.VolunteerNotFoundException;
import com.example.tgbotanimalshelter.repository.VolunteerRepository;
import org.springframework.stereotype.Service;


@Service
public class VolunteerChatService {

    private final SendMessageService sendMessageService;
    private final VolunteerRepository volunteerRepository;

    public VolunteerChatService(SendMessageService sendMessageService, VolunteerRepository volunteerRepository) {
        this.sendMessageService = sendMessageService;
        this.volunteerRepository = volunteerRepository;
    }

    public void sendMessageToUser(long userId, long volunteerId, String text) {
        sendMessageService.sendMassage(userId, text);
        sendMessageService.sendMassage(volunteerId, text);
    }

    public void sendMessageByUser(String text) {
        long volunteerId = volunteerRepository.findFirstChatId()
                .orElseThrow(VolunteerNotFoundException::new);
        sendMessageService.sendMassage(volunteerId, text);
    }

}
