package com.example.tgbotanimalshelter.listener;

import com.example.tgbotanimalshelter.entity.StatusUserChat;
import com.example.tgbotanimalshelter.service.RecordingCatService;
import com.example.tgbotanimalshelter.service.RecordingDogService;
import com.example.tgbotanimalshelter.service.UserChatService;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.User;
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

    @InjectMocks
    private TelegramBotUpdatesListener out;

    private static final String TEXT = "/";
    private static final Long CHAT_ID = 1L;
    private static final String FIRST_NAME = "username";

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
        when(user.firstName()).thenReturn(FIRST_NAME);

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

        verify(userChatService, times(4)).editUserChat(CHAT_ID, FIRST_NAME);
    }
}