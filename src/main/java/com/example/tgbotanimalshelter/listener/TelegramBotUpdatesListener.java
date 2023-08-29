package com.example.tgbotanimalshelter.listener;

import com.example.tgbotanimalshelter.command.CommandContainer;
import com.example.tgbotanimalshelter.command.CommandName;
import com.example.tgbotanimalshelter.service.SendMassageService;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class TelegramBotUpdatesListener implements UpdatesListener {
    private final TelegramBot telegramBot;

    private final CommandContainer commandContainer;

    public static final String PREF = "/";

    public TelegramBotUpdatesListener(TelegramBot telegramBot) {
        this.commandContainer = new CommandContainer(new SendMassageService(telegramBot));
        this.telegramBot = telegramBot;
    }

    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    @Override
    public int process(List<Update> updates) {
        updates.forEach(update -> {
            String massage = update.message().text();
            if (update.message() != null && massage != null && massage.startsWith(PREF)) {
                commandContainer.command(massage).execute(update);
            } else {
                new SendMassageService(telegramBot).sendMassage(update.message().chat().id(),
                        "бот поддерживает команды начинающиеся с / \n"
                        + "чтобы начать общение с ботов введите " + CommandName.START.getCommandName()
                        + " или выбериет уже предложенные ранее Вам команды");
            }

        });
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }
}
