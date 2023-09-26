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
    /**
     * The character that the command should start with
     */
    public static final String PREF = "/";

    public TelegramBotUpdatesListener(TelegramBot telegramBot,
                                      UserChatService userChatService,
                                      RecordingDogService recordingDogService,
                                      RecordingCatService recordingCatService,
                                      RecordingReportService recordingReportService) {
        this.recordingDogService = recordingDogService;
        this.recordingCatService = recordingCatService;
        this.recordingReportService = recordingReportService;
        this.commandContainer = new CommandContainer(new SendMassageService(telegramBot), userChatService);
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
                String massage = update.message().text();
                long chatId = update.message().chat().id();
                String name = update.message().from().firstName();
                userChatService.editUserChat(chatId, name);
                StatusUserChat status = userChatService.getUserCharStatus(chatId).get();
                if (massage != null) {
                    switch (status) {
                        case BASIC_STATUS -> processText(chatId, massage);
                        case WAIT_FOR_NAME_DOG -> recordingDogService.recordingName(chatId, massage);
                        case WAIT_FOR_NUMBER_DOG -> recordingDogService.recordingNumberPhone(chatId, massage);
                        case WAIT_FOR_NUMBER_CAT -> recordingCatService.recordingNumberPhoneCat(chatId, massage);
                        case WAIT_FOR_NAME_CAT -> recordingCatService.recordingNameCat(chatId, massage);
                        case WAIT_FOR_DIET -> recordingReportService.recordingDiet(chatId, massage);
                        case WAIT_FOR_WELL_BEING -> recordingReportService.recordingWellBeing(chatId, massage);
                        case WAIT_FOR_BEHAVIORS -> recordingReportService.recordingBehaviors(chatId, massage);
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
            new SendMassageService(telegramBot).sendMassage(chatId,
                    "бот поддерживает команды начинающиеся с / \n"
                            + "чтобы начать общение с ботов введите " + CommandName.START.getCommandName()
                            + " или выбериет уже предложенные ранее Вам команды");
        }
    }

}
