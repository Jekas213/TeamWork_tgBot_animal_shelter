package com.example.tgbotanimalshelter.service;

import com.example.tgbotanimalshelter.entity.CatParent;
import com.example.tgbotanimalshelter.entity.Status;
import com.example.tgbotanimalshelter.entity.UserChat;
import com.example.tgbotanimalshelter.factory.VolunteerTestFactory;
import com.example.tgbotanimalshelter.repository.CatParentRepository;
import com.example.tgbotanimalshelter.repository.CatRepository;
import com.example.tgbotanimalshelter.repository.UserChatRepository;
import com.example.tgbotanimalshelter.repository.VolunteerRepository;
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
import static com.example.tgbotanimalshelter.entity.StatusUserChat.WAIT_FOR_NAME_CAT;
import static com.example.tgbotanimalshelter.factory.CatTestFactory.buildCat;
import static com.example.tgbotanimalshelter.factory.UserChatTestFactory.buildUserChat;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class RecordingCatServiceTest extends BaseServiceTest {

    @MockBean
    private SendMessageService sendMessageService;

    @Autowired
    private RecordingCatService recordingCatService;

    @Autowired
    private CatRepository catRepository;

    @Autowired
    private CatParentRepository catParentRepository;

    @Autowired
    private UserChatRepository userChatRepository;

    @Autowired
    private VolunteerRepository volunteerRepository;

    @AfterEach
    void tearDown() {
        catParentRepository.deleteAll();
        catRepository.deleteAll();
        userChatRepository.deleteAll();
    }

    @Test
    void recordingNumberPhoneTest() {
        UserChat userChat = userChatRepository.save(buildUserChat());

        recordingCatService.recordingNumberPhoneCat(userChat.getId(), CORRECT_NUMBER);

        Optional<CatParent> catParentActual = catParentRepository.findById(userChat.getId());
        Optional<UserChat> userChatActual = userChatRepository.findById(userChat.getId());

        assertThat(catParentActual).isPresent();
        assertThat(userChatActual).isPresent();
        assertThat(catParentActual.get().getPhoneNumber()).isEqualTo(CORRECT_NUMBER);
        assertThat(userChatActual.get().getStatusUserChat()).isEqualTo(WAIT_FOR_NAME_CAT);

        Mockito.verify(sendMessageService).sendMassage(userChat.getId(), "введите имя");
    }

    @Test
    void recordingWhenIncorrectNumberPhoneTest() {
        final long userChatId = 1L;

        recordingCatService.recordingNumberPhoneCat(userChatId, INCORRECT_NUMBER);

        Mockito.verify(sendMessageService).sendMassage(userChatId, "неправильно набран номер");
    }

    @Test
    void recordingNameCatTest() {
        CatParent catParent = catParentRepository.save(buildCatParent());
        volunteerRepository.save(VolunteerTestFactory.buildVolunteer());

        recordingCatService.recordingNameCat(catParent.getChatId(), CORRECT_NAME);

        Optional<CatParent> catParentActual = catParentRepository.findById(catParent.getChatId());
        Optional<UserChat> userChatActual = userChatRepository.findById(catParent.getChatId());

        assertThat(catParentActual).isPresent();
        assertThat(userChatActual).isPresent();
        assertThat(catParentActual.get().getFullName()).isEqualTo(CORRECT_NAME);
        assertThat(userChatActual.get().getStatusUserChat()).isEqualTo(BASIC_STATUS);

        Mockito.verify(sendMessageService).sendMassage(catParent.getChatId(), "/start - Вернуться в главное меню");
    }

    @Test
    void recordingWhenIncorrectNameCatTest() {
        final long userChatId = 1L;
        recordingCatService.recordingNameCat(userChatId, INCORRECT_NAME);

        Mockito.verify(sendMessageService).sendMassage(userChatId, "введите корректное имя");
    }


    private CatParent buildCatParent() {
        return new CatParent(
                userChatRepository.save(new UserChat(1L, "name", "userChat", 1L, 1L, BASIC_STATUS)).getId(),
                "fullName",
                "9000000000",
                "address",
                Status.SEARCH,
                catRepository.save(buildCat())
        );
    }
}