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
    private static final Long chatId = 1L;
    private static final String firstName = "username";

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
        when(chat.id()).thenReturn(chatId);
        when(user.firstName()).thenReturn(firstName);

        when(userChatService.getUserChatStatus(chatId)).thenReturn(StatusUserChat.WAIT_FOR_NAME_DOG);
        out.process(List.of(update));
        verify(recordingDogService).recordingNameDog(chatId, TEXT);

        when(userChatService.getUserChatStatus(chatId)).thenReturn(StatusUserChat.WAIT_FOR_NUMBER_DOG);
        out.process(List.of(update));
        verify(recordingDogService).recordingNumberPhoneDog(chatId, TEXT);

        when(userChatService.getUserChatStatus(chatId)).thenReturn(StatusUserChat.WAIT_FOR_NAME_CAT);
        out.process(List.of(update));
        verify(recordingCatService).recordingNameCat(chatId, TEXT);

        when(userChatService.getUserChatStatus(chatId)).thenReturn(StatusUserChat.WAIT_FOR_NUMBER_CAT);
        out.process(List.of(update));
        verify(recordingCatService).recordingNumberPhoneCat(chatId, TEXT);

        verify(userChatService, times(4)).editUserChat(chatId, firstName);
    }
}