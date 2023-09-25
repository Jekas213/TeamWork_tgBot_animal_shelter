package com.example.tgbotanimalshelter.service;

import com.example.tgbotanimalshelter.controller.CatControllerTest;
import com.example.tgbotanimalshelter.controller.UserChatControllerTest;
import com.example.tgbotanimalshelter.entity.CatParent;
import com.example.tgbotanimalshelter.entity.Status;
import com.example.tgbotanimalshelter.entity.UserChat;
import com.example.tgbotanimalshelter.repository.CatParentRepository;
import com.example.tgbotanimalshelter.repository.CatRepository;
import com.example.tgbotanimalshelter.repository.UserChatRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static com.example.tgbotanimalshelter.constant.RecordingConstants.*;
import static com.example.tgbotanimalshelter.entity.StatusUserChat.BASIC_STATUS;
import static com.example.tgbotanimalshelter.entity.StatusUserChat.WAIT_FOR_NAME_CAT;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class RecordingCatServiceTest {

    @MockBean
    private SendMassageService sendMassageService;

    @Autowired
    private RecordingCatService recordingCatService;

    @Autowired
    private CatRepository catRepository;

    @Autowired
    private CatParentRepository catParentRepository;

    @Autowired
    private UserChatRepository userChatRepository;

    @AfterEach
    void tearDown() {
        catParentRepository.deleteAll();
        catRepository.deleteAll();
        userChatRepository.deleteAll();
    }

    @Test
    void recordingNumberPhoneTest() {
        UserChat userChat = userChatRepository.save(UserChatControllerTest.buildUserChat());

        recordingCatService.recordingNumberPhoneCat(userChat.getId(), CORRECT_NUMBER);

        Optional<CatParent> catParentActual = catParentRepository.findById(userChat.getId());
        Optional<UserChat> userChatActual = userChatRepository.findById(userChat.getId());

        assertThat(catParentActual).isPresent();
        assertThat(userChatActual).isPresent();
        assertThat(catParentActual.get().getPhoneNumber()).isEqualTo(CORRECT_NUMBER);
        assertThat(userChatActual.get().getStatusUserChat()).isEqualTo(WAIT_FOR_NAME_CAT);

        Mockito.verify(sendMassageService).sendMassage(userChat.getId(), "введите имя");
    }

    @Test
    void recordingWhenIncorrectNumberPhoneTest() {
        final long userChatId = 1L;

        recordingCatService.recordingNumberPhoneCat(userChatId, INCORRECT_NUMBER);

        Mockito.verify(sendMassageService).sendMassage(userChatId, "неправильно набран номер");
    }

    @Test
    void recordingNameDogTest() {
        CatParent catParent = catParentRepository.save(buildCatParent());

        recordingCatService.recordingNameCat(catParent.getChatId(), CORRECT_NAME);

        Optional<CatParent> catParentActual = catParentRepository.findById(catParent.getChatId());
        Optional<UserChat> userChatActual = userChatRepository.findById(catParent.getChatId());

        assertThat(catParentActual).isPresent();
        assertThat(userChatActual).isPresent();
        assertThat(catParentActual.get().getFullName()).isEqualTo(CORRECT_NAME);
        assertThat(userChatActual.get().getStatusUserChat()).isEqualTo(BASIC_STATUS);

        Mockito.verify(sendMassageService).sendMassage(catParent.getChatId(), "/start - Вернуться в главное меню");
    }

    @Test
    void recordingWhenIncorrectNameDogTest() {
        final long userChatId = 1L;
        recordingCatService.recordingNameCat(userChatId, INCORRECT_NAME);

        Mockito.verify(sendMassageService).sendMassage(userChatId, "введите корректное имя");
    }


    private CatParent buildCatParent() {
        return new CatParent(
                userChatRepository.save(new UserChat(1L, "userChat", BASIC_STATUS)).getId(),
                "fullName",
                "9000000000",
                "address",
                Status.SEARCH,
                catRepository.save(CatControllerTest.buildCat())
        );
    }
}