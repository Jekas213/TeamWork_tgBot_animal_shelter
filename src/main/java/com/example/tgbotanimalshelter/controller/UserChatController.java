package com.example.tgbotanimalshelter.controller;

import com.example.tgbotanimalshelter.entity.StatusUserChat;
import com.example.tgbotanimalshelter.entity.UserChat;
import com.example.tgbotanimalshelter.service.UserChatService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/userChat")
public class UserChatController extends CrudController<Long, UserChat> {

    private final UserChatService userChatService;


    public UserChatController(UserChatService userChatService) {
        super(userChatService);
        this.userChatService = userChatService;
    }

    @GetMapping("/{id}/status")
    public ResponseEntity<StatusUserChat> getUserChatStatus(@PathVariable("id") Long id) {
        return ResponseEntity.ok(userChatService.getUserChatStatus(id));
    }
}
