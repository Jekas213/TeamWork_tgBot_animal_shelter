package com.example.tgbotanimalshelter.service;

import com.example.tgbotanimalshelter.entity.UserChat;
import com.example.tgbotanimalshelter.exception.UserNotFoundException;
import com.example.tgbotanimalshelter.repository.UserChatRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static com.example.tgbotanimalshelter.entity.StatusUserChat.*;
import static com.example.tgbotanimalshelter.factory.UserChatTestFactory.buildUserChat;
import static com.example.tgbotanimalshelter.factory.UserChatTestFactory.buildUserChats;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class UserChatServiceTest extends BaseServiceTest {

    @Autowired
    private UserChatService userChatService;

    @Autowired
    private UserChatRepository userChatRepository;

    @AfterEach
    void tearDown() {
        userChatRepository.deleteAll();
    }

    @Test
    void editUserChatTest() {
        UserChat userChat = buildUserChat();

        userChatService.editUserChat(userChat.getId(), userChat.getName(), userChat.getUsername());

        assertThat(userChatRepository.existsById(userChat.getId())).isTrue();
        assertThat(userChatRepository.findById(userChat.getId())).get().isEqualTo(userChat);
    }

    @Test
    void findAllTest() {
        List<UserChat> userChats = userChatRepository.saveAll(buildUserChats());

        assertThat(userChatService.findAll()).isEqualTo(userChats);
        assertThat(userChatService.findAll()).hasSize(userChats.size());
    }

    @Test
    void findByIdTest() {
        UserChat userChat = userChatRepository.save(buildUserChat());

        assertThat(userChatService.findById(userChat.getId())).isEqualTo(userChat);
        assertThat(userChatRepository.existsById(userChat.getId())).isTrue();
    }

    @Test
    void findByIdWhenUserChatNotFoundTest() {
        assertThatExceptionOfType(UserNotFoundException.class)
                .isThrownBy(() -> userChatService.findById(1L));
    }

    @Test
    void getUserChatStatusTest() {
        UserChat userChat = userChatRepository.save(buildUserChat());

        assertThat(userChatService.getUserChatStatus(userChat.getId())).isEqualTo(userChat.getStatusUserChat());
    }

    @Test
    void inviteWaitPhoneStatusDogTest() {
        UserChat userChat = userChatRepository.save(buildUserChat());

        userChatService.inviteWaitPhoneStatusDog(userChat.getId());

        assertThat(userChatRepository.existsById(userChat.getId())).isTrue();
        assertThat(userChatService.findById(userChat.getId()).getStatusUserChat()).isEqualTo(WAIT_FOR_NUMBER_DOG);
    }

    @Test
    void inviteWaitPhoneStatusCatTest() {
        UserChat userChat = userChatRepository.save(buildUserChat());

        userChatService.inviteWaitPhoneStatusCat(userChat.getId());

        assertThat(userChatRepository.existsById(userChat.getId())).isTrue();
        assertThat(userChatService.findById(userChat.getId()).getStatusUserChat()).isEqualTo(WAIT_FOR_NUMBER_CAT);

    }

    @Test
    void inviteWaitReportStatusTest() {
        UserChat userChat = userChatRepository.save(buildUserChat());

        userChatService.inviteWaitReportStatus(userChat.getId());

        assertThat(userChatRepository.existsById(userChat.getId())).isTrue();
        assertThat(userChatService.findById(userChat.getId()).getStatusUserChat()).isEqualTo(WAIT_FOR_DIET);
    }

    @Test
    void inviteOpenChatStatusTest() {
        UserChat userChat = userChatRepository.save(buildUserChat());

        userChatService.inviteOpenChatStatus(userChat.getId());

        assertThat(userChatRepository.existsById(userChat.getId())).isTrue();
        assertThat(userChatService.findById(userChat.getId()).getStatusUserChat()).isEqualTo(OPEN_CHAT);
    }

    @Test
    void inviteBasicStatusTest() {
        UserChat userChat = userChatRepository.save(buildUserChat());

        userChatService.inviteBasicStatus(userChat.getId());

        assertThat(userChatRepository.existsById(userChat.getId())).isTrue();
        assertThat(userChatService.findById(userChat.getId()).getStatusUserChat()).isEqualTo(BASIC_STATUS);
    }

    @Test
    void updateTest() {
        UserChat userChat = userChatRepository.save(buildUserChat());

        assertThat(userChatService.update(userChat.getId(), userChat)).isEqualTo(userChat);
    }

    @Test
    void updateTestWhenUserChatNotFound() {
        UserChat userChat = buildUserChat();

        assertThatExceptionOfType(UserNotFoundException.class)
                .isThrownBy(() -> userChatService.update(userChat.getId(), userChat));
    }

    @Test
    void deleteTest() {
        UserChat userChat = userChatRepository.save(buildUserChat());

        userChatService.delete(userChat.getId());

        assertThat(userChatRepository.existsById(userChat.getId())).isFalse();
    }

    @Test
    void deleteWhenUserChatNotFound() {
        assertThatExceptionOfType(UserNotFoundException.class)
                .isThrownBy(() -> userChatService.delete(1L));
    }
}