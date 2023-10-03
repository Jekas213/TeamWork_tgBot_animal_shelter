package com.example.tgbotanimalshelter.service;

import com.example.tgbotanimalshelter.entity.DogParent;
import com.example.tgbotanimalshelter.entity.UserChat;
import com.example.tgbotanimalshelter.repository.DogParentRepository;
import org.springframework.stereotype.Service;


import static com.example.tgbotanimalshelter.entity.StatusUserChat.*;
import static com.example.tgbotanimalshelter.service.ServiceMessage.*;

@Service
public class RecordingDogService {

    private final DogParentRepository dogParentRepository;
    private final DogParentService dogParentService;
    private final UserChatService userChatService;
    private final SendMessageService sendMessageService;

    public RecordingDogService(DogParentRepository dogParentRepository, DogParentService dogParentService, UserChatService userChatService, SendMessageService sendMessageService) {
        this.dogParentRepository = dogParentRepository;
        this.dogParentService = dogParentService;
        this.userChatService = userChatService;
        this.sendMessageService = sendMessageService;
    }


    public void recordingNumberPhone(long chatId, String text) {
        if (text.matches("(\\+7|8)?(9)\\d{9}")) {
            DogParent dogParent = new DogParent();
            dogParent.setChatId(chatId);
            dogParent.setPhoneNumber(text);
            dogParentRepository.save(dogParent);
            UserChat userChat = userChatService.findById(chatId);
            userChat.setStatusUserChat(WAIT_FOR_NAME_DOG);
            userChatService.update(chatId, userChat);
            sendMessageService.sendMassage(chatId, INPUT_NAME.getCommandName());
        } else {
            sendMessageService.sendMassage(chatId, INCORRECT_NUMBER.getCommandName());

        }
    }

    public void recordingName(long chatId, String text) {
        if (text.matches("[а-яА-Я]+")) {
            long chatIdVolunteer = 1860428928;
            DogParent dogParent = dogParentService.findById(chatId);
            dogParent.setFullName(text);
            dogParentService.update(chatId, dogParent);
            UserChat userChat = userChatService.findById(chatId);
            userChat.setStatusUserChat(BASIC_STATUS);
            userChatService.update(chatId, userChat);
            sendMessageService.sendMassage(chatId, RETURN_START.getCommandName());
            sendMessageService.sendMassage(chatIdVolunteer, CONTACT_INFO.getCommandName() +
                    "\n" + "Чат id: " + chatId);
        } else {
            sendMessageService.sendMassage(chatId, INCORRECT_NAME.getCommandName());
        }

    }
}
