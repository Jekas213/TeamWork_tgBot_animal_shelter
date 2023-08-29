package com.example.tgbotanimalshelter.command;

import com.pengrad.telegrambot.model.Update;

import java.util.List;

public interface Command {
    void execute(Update update);
}
