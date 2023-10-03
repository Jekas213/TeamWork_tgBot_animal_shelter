package com.example.tgbotanimalshelter.service;

import com.example.tgbotanimalshelter.entity.DogParent;
import com.example.tgbotanimalshelter.entity.Status;
import org.springframework.stereotype.Service;

@Service
public class DogParentChangeStatus {

    private final DogParentService dogParentService;
    private final SendMessageService sendMessageService;

    public DogParentChangeStatus(DogParentService dogParentService, SendMessageService sendMessageService) {
        this.dogParentService = dogParentService;
        this.sendMessageService = sendMessageService;
    }

    public void inviteTrialStatus(long chatId){
       DogParent dogParent = dogParentService.findById(chatId);
       dogParent.setStatus(Status.TRIAL_PERIOD);
       dogParentService.update(chatId,dogParent);
       sendMessageService.sendMassage(chatId, "поздравляем вы взяли питомца");
    }

    public void inviteApprovedStatus(long chatId){
        DogParent dogParent = dogParentService.findById(chatId);
        dogParent.setStatus(Status.APPROVED);
        dogParentService.update(chatId,dogParent);
        sendMessageService.sendMassage(chatId, "поздравляем вы стали полноценным хозяином питомца");
    }

    public void inviteRefusedStatus(long chatId){
        DogParent dogParent = dogParentService.findById(chatId);
        dogParent.setStatus(Status.REFUSED);
        dogParentService.update(chatId,dogParent);
        sendMessageService.sendMassage(chatId, "заявка отклонена");
    }
}
