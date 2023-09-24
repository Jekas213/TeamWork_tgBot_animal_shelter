package com.example.tgbotanimalshelter.service;

import com.example.tgbotanimalshelter.entity.CatParent;
import com.example.tgbotanimalshelter.entity.UserChat;
import com.example.tgbotanimalshelter.repository.CatParentRepository;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

import static com.example.tgbotanimalshelter.entity.StatusUserChat.*;

@Service
public class RecordingCatService {

    private final CatParentRepository catParentRepository;
    private final CatParentService catParentService;
    private final UserChatService userChatService;
    private final SendMassageService sendMassageService;
    private static final Pattern PATTERN_NUMBER_CAT = Pattern.compile("(\\+7|8)?(9)\\d{9}");
    private static final Pattern PATTERN_NAME_CAT = Pattern.compile("[а-яА-Я]");

    public RecordingCatService(CatParentRepository catParentRepository, CatParentService catParentService, UserChatService userChatService, SendMassageService sendMassageService) {
        this.catParentRepository = catParentRepository;
        this.catParentService = catParentService;
        this.userChatService = userChatService;
        this.sendMassageService = sendMassageService;
    }

    public void recordingNumberPhoneCat(long chatId, String text) {
        if (PATTERN_NUMBER_CAT.matcher(text).find()) {
            CatParent catParent = new CatParent();
            catParent.setChatId(chatId);
            catParent.setPhoneNumber(text);
            catParentRepository.save(catParent);
            UserChat userChat = userChatService.findById(chatId).get();
            userChat.setStatusUserChat(WAIT_FOR_NAME_CAT);
            userChatService.update(chatId, userChat);
            sendMassageService.sendMassage(chatId, "введите имя");
        } else {
            sendMassageService.sendMassage(chatId, "неправильно набран номер");

        }
    }

    public void recordingNameCat(long chatId, String text) {
        if (PATTERN_NAME_CAT.matcher(text).find()) {
            CatParent catParent = catParentService.findById(chatId);
            catParent.setFullName(text);
            catParentService.update(chatId, catParent);
            UserChat userChat = userChatService.findById(chatId).get();
            userChat.setStatusUserChat(BASIC_STATUS);
            userChatService.update(chatId, userChat);
            sendMassageService.sendMassage(chatId, "/start - Вкрнуться в главное меню");
        } else {
            sendMassageService.sendMassage(chatId, "введите корректное имя");
        }

    }
}
