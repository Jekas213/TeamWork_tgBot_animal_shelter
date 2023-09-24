package com.example.tgbotanimalshelter.service;

import com.example.tgbotanimalshelter.entity.DogParent;
import com.example.tgbotanimalshelter.entity.UserChat;
import com.example.tgbotanimalshelter.repository.DogParentRepository;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.example.tgbotanimalshelter.entity.StatusUserChat.*;

@Service
public class RecordingDogService {

    private final DogParentRepository dogParentRepository;
    private final DogParentService dogParentService;
    private final UserChatService userChatService;
    private final SendMassageService sendMassageService;
    private static final Pattern PATTERN_NUMBER = Pattern.compile("(\\+7|8)?(9)\\d{9}");
    private static final Pattern PATTERN_NAME = Pattern.compile("[а-яА-Я]");

    public RecordingDogService(DogParentRepository dogParentRepository, DogParentService dogParentService, UserChatService userChatService, SendMassageService sendMassageService) {
        this.dogParentRepository = dogParentRepository;
        this.dogParentService = dogParentService;
        this.userChatService = userChatService;
        this.sendMassageService = sendMassageService;
    }


    public void recordingNumberPhone(long chatId, String text) {
        Matcher matcher = PATTERN_NUMBER.matcher(text);
        if (matcher.matches()) {
            DogParent dogParent = new DogParent();
            dogParent.setChatId(chatId);
            dogParent.setPhoneNumber(text);
            dogParentRepository.save(dogParent);
            UserChat userChat = userChatService.findById(chatId).get();
            userChat.setStatusUserChat(WAIT_FOR_NAME_DOG);
            userChatService.update(chatId, userChat);
            sendMassageService.sendMassage(chatId, "введите имя");
        } else {
            sendMassageService.sendMassage(chatId, "неправильно набран номер");

        }
    }

    public void recordingName(long chatId, String text) {
        if (PATTERN_NAME.matcher(text).find()) {
            DogParent dogParent = dogParentService.findById(chatId);
            dogParent.setFullName(text);
            dogParentService.update(chatId, dogParent);
            UserChat userChat = userChatService.findById(chatId).get();
            userChat.setStatusUserChat(BASIC_STATUS);
            userChatService.update(chatId, userChat);
            sendMassageService.sendMassage(chatId, "/start - Вкрнуться в главное меню");
        } else {
            sendMassageService.sendMassage(chatId, "введите корректное имя");
        }

    }
}
