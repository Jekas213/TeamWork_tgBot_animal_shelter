package com.example.tgbotanimalshelter.controller;

import com.example.tgbotanimalshelter.entity.Volunteer;
import com.example.tgbotanimalshelter.service.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/volunteer")
public class VolunteerController extends CrudController<Long, Volunteer> {
    private final SendMessageService sendMessageService;
    private final VolunteerChatService volunteerChatService;
    private final UserChatService userChatService;
    private final DogParentChangeStatus dogParentChangeStatus;
    private final CatParentChangeStatus catParentChangeStatus;

    public VolunteerController(VolunteerService volunteerService, SendMessageService sendMessageService, VolunteerChatService volunteerChatService, UserChatService userChatService, DogParentChangeStatus dogParentChangeStatus, CatParentChangeStatus catParentChangeStatus) {
        super(volunteerService);
        this.sendMessageService = sendMessageService;
        this.volunteerChatService = volunteerChatService;
        this.userChatService = userChatService;
        this.dogParentChangeStatus = dogParentChangeStatus;
        this.catParentChangeStatus = catParentChangeStatus;
    }

    @PatchMapping("/badReport/{id}")
    public void sendMassageBadReport(@PathVariable long id, @RequestParam(required = false) String text) {
        if (text != null) {
            sendMessageService.sendMassage(id, text);
        } else {
            sendMessageService.sendMassage(id, ServiceMessage.BAD_REPORT.getCommandName());
        }

    }

    @PatchMapping("/message/{userId}/{volunteerId}")
    public void sendMassageFromVolunteer(@PathVariable long userId, @PathVariable long volunteerId, @RequestParam String text) {
        volunteerChatService.sendMessageToUser(userId, volunteerId, text);
    }

    @PatchMapping("/openChat/{id}")
    public void openChatByVolunteer(@PathVariable long id) {
        userChatService.inviteOpenChatStatus(id);
    }

    @PatchMapping("/closeChat/{id}")
    public void closeChatByVolunteer(@PathVariable long id) {
        userChatService.inviteBasicStatus(id);
    }

    @PatchMapping("/dog/trial/{id}")
    public void changeByTrialDog(@PathVariable long id) {
        dogParentChangeStatus.inviteTrialStatus(id);
    }

    @PatchMapping("/dog/approved/{id}")
    public void changeByApprovedDog(@PathVariable long id) {
        dogParentChangeStatus.inviteApprovedStatus(id);
    }

    @PatchMapping("/dog/refused/{id}")
    public void changeByRefusedDog(@PathVariable long id) {
        dogParentChangeStatus.inviteRefusedStatus(id);
    }

    @PatchMapping("/cat/trial/{id}")
    public void changeByTrialCat(@PathVariable long id) {
        catParentChangeStatus.inviteTrialStatus(id);
    }

    @PatchMapping("/cat/approved/{id}")
    public void changeByApprovedCat(@PathVariable long id) {
        catParentChangeStatus.inviteApprovedStatus(id);
    }

    @PatchMapping("/cat/refused/{id}")
    public void changeByRefusedCat(@PathVariable long id) {
        catParentChangeStatus.inviteRefusedStatus(id);
    }

}
