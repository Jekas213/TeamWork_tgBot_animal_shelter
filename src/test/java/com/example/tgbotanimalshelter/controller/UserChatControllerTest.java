package com.example.tgbotanimalshelter.controller;

import com.example.tgbotanimalshelter.entity.StatusUserChat;
import com.example.tgbotanimalshelter.entity.UserChat;
import com.example.tgbotanimalshelter.exception.UserNotFoundException;
import com.example.tgbotanimalshelter.repository.UserChatRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
public class UserChatControllerTest extends BaseControllerTest {

    private static final String ROOT = "/userChat";


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserChatRepository userChatRepository;

    @Autowired
    private ObjectMapper objectMapper;


    @AfterEach
    void tearDown() {
        userChatRepository.deleteAll();
    }

    @Test
    void findAllTest() throws Exception {
        List<UserChat> userChats = userChatRepository.saveAll(buildUserChats());

        mockMvc.perform(get(ROOT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(result -> {
                    List<UserChat> actual = objectMapper.readValue(result.getResponse().getContentAsString(),
                            new TypeReference<>() {
                            });

                    assertThat(actual).hasSize(userChats.size());
                    assertThat(actual).isEqualTo(userChats);
                });
    }

    @Test
    void findByIdTest() throws Exception {
        UserChat userChat = userChatRepository.save(buildUserChat());

        mockMvc.perform(get(ROOT + "/{id}", userChat.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(result -> {
                    UserChat actual = objectMapper.readValue(result.getResponse().getContentAsString(), UserChat.class);

                    assertThat(actual).isEqualTo(userChat);
                });
    }

    @Test
    void findByIdWhenUserChatNotFoundTest() throws Exception {
        mockMvc.perform(get(ROOT + "/{id}", 1L))
                .andExpect(status().isNotFound())
                .andExpect(result ->
                        assertThat(result.getResolvedException().getClass()).isEqualTo(UserNotFoundException.class));
    }

    public static UserChat buildUserChat() {
        return new UserChat(1L, "username", StatusUserChat.BASIC_STATUS);
    }

    private List<UserChat> buildUserChats(int count) {
        return LongStream.range(1, count)
                .mapToObj(i -> new UserChat(i, "username" + i, StatusUserChat.BASIC_STATUS))
                .collect(Collectors.toList());
    }

    private List<UserChat> buildUserChats() {
        return buildUserChats(10);
    }
}
