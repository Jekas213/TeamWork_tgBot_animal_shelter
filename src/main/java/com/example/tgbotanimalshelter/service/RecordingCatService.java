package com.example.tgbotanimalshelter.service;

import com.example.tgbotanimalshelter.entity.CatParent;
import com.example.tgbotanimalshelter.entity.UserChat;
import com.example.tgbotanimalshelter.repository.CatParentRepository;
import org.springframework.stereotype.Service;


import static com.example.tgbotanimalshelter.entity.StatusUserChat.*;
import static com.example.tgbotanimalshelter.service.ServiceMessage.*;

@Service
public class RecordingCatService {

    private final CatParentRepository catParentRepository;
    private final CatParentService catParentService;
    private final UserChatService userChatService;
    private final SendMessageService sendMessageService;

    public RecordingCatService(CatParentRepository catParentRepository, CatParentService catParentService, UserChatService userChatService, SendMessageService sendMessageService) {
        this.catParentRepository = catParentRepository;
        this.catParentService = catParentService;
        this.userChatService = userChatService;
        this.sendMessageService = sendMessageService;
    }

    public void recordingNumberPhoneCat(long chatId, String text) {
        if (text.matches("(\\+7|8)?(9)\\d{9}")) {
            CatParent catParent = new CatParent();
            catParent.setChatId(chatId);
            catParent.setPhoneNumber(text);
            catParentRepository.save(catParent);
            UserChat userChat = userChatService.findById(chatId);
            userChat.setStatusUserChat(WAIT_FOR_NAME_CAT);
            userChatService.update(chatId, userChat);
            sendMessageService.sendMassage(chatId, INPUT_NAME.getCommandName());
        } else {
            sendMessageService.sendMassage(chatId, INCORRECT_NUMBER.getCommandName());

        }
    }

    public void recordingNameCat(long chatId, String text) {
        if (text.matches("[а-яА-Я]+")) {
            long chatIdVolunteer = 1860428928;
            CatParent catParent = catParentService.findById(chatId);
            catParent.setFullName(text);
            catParentService.update(chatId, catParent);
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
