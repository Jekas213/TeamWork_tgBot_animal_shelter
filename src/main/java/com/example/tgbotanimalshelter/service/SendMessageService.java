package com.example.tgbotanimalshelter.service;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.BaseRequest;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.stereotype.Service;


@Service
public class SendMessageService {
    private final TelegramBot telegramBot;

    public SendMessageService(TelegramBot telegramBot) {
        this.telegramBot = telegramBot;
    }

    /**
     * Sends a message to the user<br>
     * Uses the method {@link TelegramBot#execute(BaseRequest)}
     * @param chatId
     * @param message message text
     */
    public void sendMassage(long chatId, String message) {
        SendMessage sendMessage = new SendMessage(chatId, message);
        telegramBot.execute(sendMessage);
    }
}
