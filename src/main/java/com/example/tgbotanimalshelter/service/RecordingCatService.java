package com.example.tgbotanimalshelter.service;

import com.example.tgbotanimalshelter.entity.CatParent;
import com.example.tgbotanimalshelter.entity.UserChat;
import com.example.tgbotanimalshelter.repository.CatParentRepository;
import org.springframework.stereotype.Service;


import static com.example.tgbotanimalshelter.entity.StatusUserChat.*;
import static com.example.tgbotanimalshelter.service.ServiceMassage.*;

@Service
public class RecordingCatService {

    private final CatParentRepository catParentRepository;
    private final CatParentService catParentService;
    private final UserChatService userChatService;
    private final SendMassageService sendMassageService;

    public RecordingCatService(CatParentRepository catParentRepository, CatParentService catParentService, UserChatService userChatService, SendMassageService sendMassageService) {
        this.catParentRepository = catParentRepository;
        this.catParentService = catParentService;
        this.userChatService = userChatService;
        this.sendMassageService = sendMassageService;
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
            sendMassageService.sendMassage(chatId, INPUT_NAME.getCommandName());
        } else {
            sendMassageService.sendMassage(chatId, INCORRECT_NUMBER.getCommandName());

        }
    }

    public void recordingNameCat(long chatId, String text) {
        if (text.matches("[а-яА-Я]+")) {
            CatParent catParent = catParentService.findById(chatId);
            catParent.setFullName(text);
            catParentService.update(chatId, catParent);
            UserChat userChat = userChatService.findById(chatId);
            userChat.setStatusUserChat(BASIC_STATUS);
            userChatService.update(chatId, userChat);
            sendMassageService.sendMassage(chatId, RETURN_START.getCommandName());
        } else {
            sendMassageService.sendMassage(chatId, INCORRECT_NAME.getCommandName());
        }

    }
}
