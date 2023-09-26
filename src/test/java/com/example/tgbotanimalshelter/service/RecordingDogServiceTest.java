package com.example.tgbotanimalshelter.service;

import com.example.tgbotanimalshelter.controller.DogControllerTest;
import com.example.tgbotanimalshelter.controller.UserChatControllerTest;
import com.example.tgbotanimalshelter.entity.DogParent;
import com.example.tgbotanimalshelter.entity.Status;
import com.example.tgbotanimalshelter.entity.StatusUserChat;
import com.example.tgbotanimalshelter.entity.UserChat;
import com.example.tgbotanimalshelter.repository.DogParentRepository;
import com.example.tgbotanimalshelter.repository.DogRepository;
import com.example.tgbotanimalshelter.repository.UserChatRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static com.example.tgbotanimalshelter.constant.RecordingConstants.*;
import static com.example.tgbotanimalshelter.entity.StatusUserChat.BASIC_STATUS;
import static com.example.tgbotanimalshelter.entity.StatusUserChat.WAIT_FOR_NAME_DOG;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class RecordingDogServiceTest {

    @MockBean
    private SendMassageService sendMassageService;

    @Autowired
    private RecordingDogService recordingDogService;

    @Autowired
    private DogRepository dogRepository;

    @Autowired
    private DogParentRepository dogParentRepository;

    @Autowired
    private UserChatRepository userChatRepository;

    @AfterEach
    void tearDown() {
        dogParentRepository.deleteAll();
        dogRepository.deleteAll();
        userChatRepository.deleteAll();
    }

    @Test
    void recordingNumberPhoneTest() {
        UserChat userChat = userChatRepository.save(UserChatControllerTest.buildUserChat());

        recordingDogService.recordingNumberPhoneDog(userChat.getId(), CORRECT_NUMBER);

        Optional<DogParent> dogParentActual = dogParentRepository.findById(userChat.getId());
        Optional<UserChat> userChatActual = userChatRepository.findById(userChat.getId());

        assertThat(dogParentActual).isPresent();
        assertThat(userChatActual).isPresent();
        assertThat(dogParentActual.get().getPhoneNumber()).isEqualTo(CORRECT_NUMBER);
        assertThat(userChatActual.get().getStatusUserChat()).isEqualTo(WAIT_FOR_NAME_DOG);

        Mockito.verify(sendMassageService).sendMassage(userChat.getId(), "введите имя");
    }

    @Test
    void recordingWhenIncorrectNumberPhoneTest() {
        final long userChatId = 1L;

        recordingDogService.recordingNumberPhoneDog(userChatId, INCORRECT_NUMBER);

        Mockito.verify(sendMassageService).sendMassage(userChatId, "неправильно набран номер");
    }

    @Test
    void recordingNameDogTest() {
        DogParent dogParent = dogParentRepository.save(buildDogParent());

        recordingDogService.recordingNameDog(dogParent.getChatId(), CORRECT_NAME);

        Optional<DogParent> dogParentActual = dogParentRepository.findById(dogParent.getChatId());
        Optional<UserChat> userChatActual = userChatRepository.findById(dogParent.getChatId());

        assertThat(dogParentActual).isPresent();
        assertThat(userChatActual).isPresent();
        assertThat(dogParentActual.get().getFullName()).isEqualTo(CORRECT_NAME);
        assertThat(userChatActual.get().getStatusUserChat()).isEqualTo(BASIC_STATUS);

        Mockito.verify(sendMassageService).sendMassage(dogParent.getChatId(), "/start - Вернуться в главное меню");
    }

    @Test
    void recordingWhenIncorrectNameDogTest() {
        final long userChatId = 1L;
        recordingDogService.recordingNameDog(userChatId, INCORRECT_NAME);

        Mockito.verify(sendMassageService).sendMassage(userChatId, "введите корректное имя");
    }

    private DogParent buildDogParent() {
        return new DogParent(
                userChatRepository.save(new UserChat(1L, "userChat", StatusUserChat.BASIC_STATUS)).getId(),
                "fullName",
                "9000000000",
                "address",
                Status.SEARCH,
                dogRepository.save(DogControllerTest.buildDog())
        );
    }
}