package com.example.tgbotanimalshelter.listener;

import com.example.tgbotanimalshelter.command.CommandContainer;
import com.example.tgbotanimalshelter.command.CommandName;
import com.example.tgbotanimalshelter.entity.StatusUserChat;
import com.example.tgbotanimalshelter.service.*;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.PhotoSize;
import com.pengrad.telegrambot.model.Update;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

import static com.example.tgbotanimalshelter.entity.StatusUserChat.*;

@Service
public class TelegramBotUpdatesListener implements UpdatesListener {
    private final TelegramBot telegramBot;
    private final CommandContainer commandContainer;
    private final UserChatService userChatService;
    private final RecordingDogService recordingDogService;
    private final RecordingCatService recordingCatService;
    private final RecordingReportService recordingReportService;
    private final VolunteerChatService volunteerChatService;
    private final VolunteerService volunteerService;
    private final DogParentService dogParentService;
    private final CatParentService catParentService;
    /**
     * The character that the command should start with
     */
    public static final String PREF = "/";

    public TelegramBotUpdatesListener(TelegramBot telegramBot,
                                      CommandContainer commandContainer, UserChatService userChatService,
                                      RecordingDogService recordingDogService,
                                      RecordingCatService recordingCatService,
                                      RecordingReportService recordingReportService,
                                      VolunteerChatService volunteerChatService,
                                      VolunteerService volunteerService,
                                      DogParentService dogParentService,
                                      CatParentService catParentService) {
        this.commandContainer = commandContainer;
        this.recordingDogService = recordingDogService;
        this.recordingCatService = recordingCatService;
        this.recordingReportService = recordingReportService;
        this.volunteerChatService = volunteerChatService;
        this.volunteerService = volunteerService;
        this.dogParentService = dogParentService;
        this.catParentService = catParentService;
        this.telegramBot = telegramBot;
        this.userChatService = userChatService;
    }

    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    @Override
    public int process(List<Update> updates) {
        updates.forEach(update -> {
            if (update.message() != null) {
                PhotoSize[] photoSizes = update.message().photo();
                String message = update.message().text();
                long chatId = update.message().chat().id();
                String name = update.message().from().firstName();
                String userName = update.message().from().username();
                userChatService.editUserChat(chatId, name, userName);
                StatusUserChat status = userChatService.getUserChatStatus(chatId);
                if (message != null) {
                    switch (status) {
                        case BASIC_STATUS -> processText(chatId, message);
                        case OPEN_CHAT -> volunteerChatService.sendMessageByUser(chatId, message);
                        case WAIT_FOR_NAME_DOG -> recordingDogService.recordingName(chatId, message);
                        case WAIT_FOR_NUMBER_DOG -> recordingDogService.recordingNumberPhone(chatId, message);
                        case WAIT_FOR_NUMBER_CAT -> recordingCatService.recordingNumberPhoneCat(chatId, message);
                        case WAIT_FOR_NAME_CAT -> recordingCatService.recordingNameCat(chatId, message);
                        case WAIT_FOR_DIET -> recordingReportService.recordingDiet(chatId, message);
                        case WAIT_FOR_WELL_BEING -> recordingReportService.recordingWellBeing(chatId, message);
                        case WAIT_FOR_BEHAVIORS -> recordingReportService.recordingBehaviors(chatId, message);
                    }

                } else if (update.message().photo() != null) {
                    if (WAIT_FOR_PICTURE.equals(status)) {
                        recordingReportService.recordingPhoto(chatId, photoSizes);
                    }
                }
            }

        });
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

    private void processText(long chatId, String massage) {
        if (massage.startsWith(PREF)) {
            commandContainer.command(massage).execute(chatId);
        } else {
            new SendMessageService(telegramBot).sendMassage(chatId,
                    "бот поддерживает команды начинающиеся с / \n"
                            + "чтобы начать общение с ботов введите " + CommandName.START.getCommandName()
                            + " или выбериет уже предложенные ранее Вам команды");
        }
    }

}
