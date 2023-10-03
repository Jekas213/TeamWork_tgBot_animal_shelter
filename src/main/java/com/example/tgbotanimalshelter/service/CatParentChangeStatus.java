package com.example.tgbotanimalshelter.service;

import com.example.tgbotanimalshelter.entity.CatParent;
import com.example.tgbotanimalshelter.entity.Status;
import org.springframework.stereotype.Service;

@Service
public class CatParentChangeStatus {

    private final CatParentService catParentService;
    private final SendMessageService sendMessageService;

    public CatParentChangeStatus(CatParentService catParentService, SendMessageService sendMessageService) {
        this.catParentService = catParentService;
        this.sendMessageService = sendMessageService;
    }

    public void inviteTrialStatus(long chatId){
        CatParent catParent = catParentService.findById(chatId);
        catParent.setStatus(Status.TRIAL_PERIOD);
        catParentService.update(chatId,catParent);
        sendMessageService.sendMassage(chatId, "поздравляем вы взяли питомца");
    }

    public void inviteApprovedStatus(long chatId){
        CatParent catParent = catParentService.findById(chatId);
        catParent.setStatus(Status.APPROVED);
        catParentService.update(chatId,catParent);
        sendMessageService.sendMassage(chatId, "поздравляем вы стали полноценным хозяином питомца");
    }

    public void inviteRefusedStatus(long chatId){
        CatParent catParent = catParentService.findById(chatId);
        catParent.setStatus(Status.REFUSED);
        catParentService.update(chatId,catParent);
        sendMessageService.sendMassage(chatId, "заявка отклонена");
    }
}
