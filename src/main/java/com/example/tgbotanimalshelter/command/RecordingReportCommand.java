package com.example.tgbotanimalshelter.command;

import com.example.tgbotanimalshelter.entity.Status;
import com.example.tgbotanimalshelter.exception.CatParentNotFoundException;
import com.example.tgbotanimalshelter.exception.DogParentNotFoundException;
import com.example.tgbotanimalshelter.service.CatParentService;
import com.example.tgbotanimalshelter.service.DogParentService;
import com.example.tgbotanimalshelter.service.SendMessageService;
import com.example.tgbotanimalshelter.service.UserChatService;

import static com.example.tgbotanimalshelter.command.EnumOtherInfo.*;

public class RecordingReportCommand implements Command {
    private final SendMessageService sendMessageService;
    private final String text;
    private final UserChatService userChatService;
    private final DogParentService dogParentService;
    private final CatParentService catParentService;

    public RecordingReportCommand(SendMessageService sendMessageService, String text,
                                  UserChatService userChatService,
                                  DogParentService dogParentService,
                                  CatParentService catParentService) {
        this.sendMessageService = sendMessageService;
        this.text = text;
        this.userChatService = userChatService;
        this.catParentService = catParentService;
        this.dogParentService = dogParentService;
    }

    @Override
    public void execute(long chatId) {
        if (validateCommand(chatId)) {
            sendMessageService.sendMassage(chatId, text);
            userChatService.inviteWaitReportStatus(chatId);
        } else {
            sendMessageService.sendMassage(chatId, NOT_ANIMALS.getCommandName());
        }
    }

    private boolean validateCommand(long chatId) {
        try {
            return catParentService.getCatParentStatus(chatId).equals(Status.TRIAL_PERIOD) || dogParentService.getDogParentStatus(chatId).equals(Status.TRIAL_PERIOD);
        } catch (CatParentNotFoundException | DogParentNotFoundException e) {
            return false;
        }
    }
}
