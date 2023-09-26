package com.example.tgbotanimalshelter.controller;

import com.example.tgbotanimalshelter.entity.UserChat;
import com.example.tgbotanimalshelter.service.UserChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/userChat")
@RequiredArgsConstructor
public class UserChatController {

    private final UserChatService userChatService;

    @GetMapping
    public ResponseEntity<List<UserChat>> findAll() {
        return ResponseEntity.ok(userChatService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserChat> findById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(userChatService.findById(id));
    }
}
