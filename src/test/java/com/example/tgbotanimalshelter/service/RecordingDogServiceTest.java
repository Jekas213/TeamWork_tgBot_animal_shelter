package com.example.tgbotanimalshelter.service;

import com.example.tgbotanimalshelter.entity.*;
import com.example.tgbotanimalshelter.factory.VolunteerTestFactory;
import com.example.tgbotanimalshelter.repository.DogParentRepository;
import com.example.tgbotanimalshelter.repository.DogRepository;
import com.example.tgbotanimalshelter.repository.UserChatRepository;
import com.example.tgbotanimalshelter.repository.VolunteerRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static com.example.tgbotanimalshelter.constant.RecordingConstants.*;
import static com.example.tgbotanimalshelter.entity.StatusUserChat.BASIC_STATUS;
import static com.example.tgbotanimalshelter.entity.StatusUserChat.WAIT_FOR_NAME_DOG;
import static com.example.tgbotanimalshelter.factory.DogTestFactory.buildDog;
import static com.example.tgbotanimalshelter.factory.UserChatTestFactory.buildUserChat;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class RecordingDogServiceTest extends BaseServiceTest {

    @MockBean
    private SendMessageService sendMessageService;

    @Autowired
    private RecordingDogService recordingDogService;

    @Autowired
    private DogRepository dogRepository;

    @Autowired
    private DogParentRepository dogParentRepository;

    @Autowired
    private UserChatRepository userChatRepository;

    @Autowired
    private VolunteerRepository volunteerRepository;

    @AfterEach
    void tearDown() {
        dogParentRepository.deleteAll();
        dogRepository.deleteAll();
        userChatRepository.deleteAll();
    }

    @Test
    void recordingNumberPhoneTest() {
        UserChat userChat = userChatRepository.save(buildUserChat());

        recordingDogService.recordingNumberPhone(userChat.getId(), CORRECT_NUMBER);

        Optional<DogParent> dogParentActual = dogParentRepository.findById(userChat.getId());
        Optional<UserChat> userChatActual = userChatRepository.findById(userChat.getId());

        assertThat(dogParentActual).isPresent();
        assertThat(userChatActual).isPresent();
        assertThat(dogParentActual.get().getPhoneNumber()).isEqualTo(CORRECT_NUMBER);
        assertThat(userChatActual.get().getStatusUserChat()).isEqualTo(WAIT_FOR_NAME_DOG);

        Mockito.verify(sendMessageService).sendMassage(userChat.getId(), "введите имя");
    }

    @Test
    void recordingWhenIncorrectNumberPhoneTest() {
        final long userChatId = 1L;

        recordingDogService.recordingNumberPhone(userChatId, INCORRECT_NUMBER);

        Mockito.verify(sendMessageService).sendMassage(userChatId, "неправильно набран номер");
    }

    @Test
    void recordingNameDogTest() {
        DogParent dogParent = dogParentRepository.save(buildDogParent());
        volunteerRepository.save(VolunteerTestFactory.buildVolunteer());

        recordingDogService.recordingName(dogParent.getChatId(), CORRECT_NAME);

        Optional<DogParent> dogParentActual = dogParentRepository.findById(dogParent.getChatId());
        Optional<UserChat> userChatActual = userChatRepository.findById(dogParent.getChatId());

        assertThat(dogParentActual).isPresent();
        assertThat(userChatActual).isPresent();
        assertThat(dogParentActual.get().getFullName()).isEqualTo(CORRECT_NAME);
        assertThat(userChatActual.get().getStatusUserChat()).isEqualTo(BASIC_STATUS);

        Mockito.verify(sendMessageService).sendMassage(dogParent.getChatId(), "/start - Вернуться в главное меню");
    }

    @Test
    void recordingWhenIncorrectNameDogTest() {
        final long userChatId = 1L;
        recordingDogService.recordingName(userChatId, INCORRECT_NAME);

        Mockito.verify(sendMessageService).sendMassage(userChatId, "введите корректное имя");
    }

    private DogParent buildDogParent() {
        return new DogParent(
                userChatRepository.save(new UserChat(1L, "name", "userChat", 1L, 1L, StatusUserChat.BASIC_STATUS)).getId(),
                "fullName",
                "9000000000",
                "address",
                Status.SEARCH,
                dogRepository.save(buildDog())
        );
    }
}