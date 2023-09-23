package com.example.tgbotanimalshelter.listener;

import com.example.tgbotanimalshelter.command.CommandContainer;
import com.example.tgbotanimalshelter.command.CommandName;
import com.example.tgbotanimalshelter.entity.StatusUserChat;
import com.example.tgbotanimalshelter.entity.UserChat;
import com.example.tgbotanimalshelter.service.SendMassageService;
import com.example.tgbotanimalshelter.service.UserChatService;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;

import static com.example.tgbotanimalshelter.entity.StatusUserChat.*;

@Service
public class TelegramBotUpdatesListener implements UpdatesListener {
    private final TelegramBot telegramBot;

    private final CommandContainer commandContainer;

    private final UserChatService userChatService;
    /**
     * The character that the command should start with
     */
    public static final String PREF = "/";

    public TelegramBotUpdatesListener(TelegramBot telegramBot, UserChatService userChatService) {
        this.commandContainer = new CommandContainer(new SendMassageService(telegramBot));
        this.telegramBot = telegramBot;
        this.userChatService = userChatService;
    }

    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    @Override
    public int process(List<Update> updates) {
        updates.forEach(update -> {
            String massage = update.message().text();
            long chatId = update.message().chat().id();
            String name = update.message().from().firstName();
            userChatService.editUserChat(chatId, name);
            StatusUserChat status = userChatService.getUserCharStatus(chatId).get();
            if (update.message() != null && massage != null) {
                if (BASIC_STATUS.equals(status)){
                    processText(chatId,massage);
                } else if (WAIT_FOR_NAME.equals(status)){
                    //
                } else if (WAIT_FOR_NUMBER.equals(status)){
                    //
                }
            }

        });
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

    private void processText(long chatId, String massage) {
        if (massage.startsWith(PREF)) {
            commandContainer.command(massage).execute(chatId);
        } else {
            new SendMassageService(telegramBot).sendMassage(chatId,
                    "бот поддерживает команды начинающиеся с / \n"
                            + "чтобы начать общение с ботов введите " + CommandName.START.getCommandName()
                            + " или выбериет уже предложенные ранее Вам команды");
        }
    }

}
