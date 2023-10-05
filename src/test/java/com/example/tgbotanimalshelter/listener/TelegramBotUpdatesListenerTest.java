package com.example.tgbotanimalshelter.listener;

import com.example.tgbotanimalshelter.entity.StatusUserChat;
import com.example.tgbotanimalshelter.service.*;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TelegramBotUpdatesListenerTest {

    @Mock
    private TelegramBot telegramBot;

    @Mock
    private UserChatService userChatService;

    @Mock
    private RecordingDogService recordingDogService;

    @Mock
    private RecordingCatService recordingCatService;
    @Mock
    private User user;

    @Mock
    private Chat chat;

    @Mock
    private Update update;

    @Mock
    private Message message;

    @Mock
    private VolunteerChatService volunteerChatService;

    @Mock
    private RecordingReportService recordingReportService;

    @InjectMocks
    private TelegramBotUpdatesListener out;

    private static final String TEXT = "/";
    private static final Long CHAT_ID = 1L;
    private static final String NAME = "name";

    private static final String USERNAME = "username";

    @Test
    void initTest() {
        out.init();
        verify(telegramBot).setUpdatesListener(any());
    }

    @Test
    void processTest() {
        when(update.message()).thenReturn(message);
        when(message.text()).thenReturn(TEXT);
        when(message.chat()).thenReturn(chat);
        when(message.from()).thenReturn(user);
        when(chat.id()).thenReturn(CHAT_ID);
        when(user.firstName()).thenReturn(NAME);
        when(user.username()).thenReturn(USERNAME);

        when(userChatService.getUserChatStatus(CHAT_ID)).thenReturn(StatusUserChat.OPEN_CHAT);
        out.process(List.of(update));
        verify(volunteerChatService).sendMessageByUser(CHAT_ID, TEXT);

        when(userChatService.getUserChatStatus(CHAT_ID)).thenReturn(StatusUserChat.WAIT_FOR_NAME_DOG);
        out.process(List.of(update));
        verify(recordingDogService).recordingName(CHAT_ID, TEXT);

        when(userChatService.getUserChatStatus(CHAT_ID)).thenReturn(StatusUserChat.WAIT_FOR_NUMBER_DOG);
        out.process(List.of(update));
        verify(recordingDogService).recordingNumberPhone(CHAT_ID, TEXT);

        when(userChatService.getUserChatStatus(CHAT_ID)).thenReturn(StatusUserChat.WAIT_FOR_NAME_CAT);
        out.process(List.of(update));
        verify(recordingCatService).recordingNameCat(CHAT_ID, TEXT);

        when(userChatService.getUserChatStatus(CHAT_ID)).thenReturn(StatusUserChat.WAIT_FOR_NUMBER_CAT);
        out.process(List.of(update));
        verify(recordingCatService).recordingNumberPhoneCat(CHAT_ID, TEXT);

        when(userChatService.getUserChatStatus(CHAT_ID)).thenReturn(StatusUserChat.WAIT_FOR_DIET);
        out.process(List.of(update));
        verify(recordingReportService).recordingDiet(CHAT_ID, TEXT);

        when(userChatService.getUserChatStatus(CHAT_ID)).thenReturn(StatusUserChat.WAIT_FOR_WELL_BEING);
        out.process(List.of(update));
        verify(recordingReportService).recordingWellBeing(CHAT_ID, TEXT);

        when(userChatService.getUserChatStatus(CHAT_ID)).thenReturn(StatusUserChat.WAIT_FOR_BEHAVIORS);
        out.process(List.of(update));
        verify(recordingReportService).recordingBehaviors(CHAT_ID, TEXT);

        verify(userChatService, times(8)).editUserChat(CHAT_ID, NAME, USERNAME);
    }


    @Test
    void processWhenTextIsNullTest() {
        when(update.message()).thenReturn(message);
        PhotoSize[] photoSizes = new PhotoSize[]{};
        when(update.message().photo()).thenReturn(photoSizes);
        when(message.text()).thenReturn(null);
        when(message.chat()).thenReturn(chat);
        when(message.from()).thenReturn(user);
        when(chat.id()).thenReturn(CHAT_ID);
        when(user.firstName()).thenReturn(NAME);
        when(user.username()).thenReturn(USERNAME);

        when(userChatService.getUserChatStatus(CHAT_ID)).thenReturn(StatusUserChat.WAIT_FOR_PICTURE);
        out.process(List.of(update));
        verify(recordingReportService).recordingPhoto(CHAT_ID, photoSizes);

        verify(userChatService, times(1)).editUserChat(CHAT_ID, NAME, USERNAME);
    }
}