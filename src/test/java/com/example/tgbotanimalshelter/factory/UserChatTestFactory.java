package com.example.tgbotanimalshelter.factory;

import com.example.tgbotanimalshelter.entity.StatusUserChat;
import com.example.tgbotanimalshelter.entity.UserChat;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

public class UserChatTestFactory {
    public static UserChat buildUserChat() {
        return new UserChat(1L, "name", "username", StatusUserChat.BASIC_STATUS);
    }

    public static List<UserChat> buildUserChats(int count) {
        return LongStream.range(1, count)
                .mapToObj(i -> new UserChat(i, "name" + i, "username" + i, StatusUserChat.BASIC_STATUS))
                .collect(Collectors.toList());
    }

    public static List<UserChat> buildUserChats() {
        return buildUserChats(10);
    }

}
