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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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

    @Test
    void createTest() throws Exception {
        UserChat userChat = buildUserChat();

        mockMvc.perform(post(ROOT)
                        .content(objectMapper.writeValueAsString(userChat))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(result -> {
                    UserChat actual = objectMapper.readValue(result.getResponse().getContentAsString(), UserChat.class);

                    assertThat(actual)
                            .usingRecursiveComparison()
                            .ignoringFields("id")
                            .isEqualTo(userChat);
                    assertThat(userChatRepository.existsById(actual.getId())).isTrue();
                });
    }

    @Test
    void updateTest() throws Exception {
        UserChat userChat = userChatRepository.save(buildUserChat());

        mockMvc.perform(put(ROOT + "/{id}", userChat.getId())
                        .content(objectMapper.writeValueAsString(userChat))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(result -> {
                    UserChat actual = objectMapper.readValue(result.getResponse().getContentAsString(), UserChat.class);

                    assertThat(actual).isEqualTo(userChat);
                });
    }


    @Test
    void updateWhenUserChatNotFoundTest() throws Exception {
        UserChat userChat = buildUserChat();

        mockMvc.perform(put(ROOT + "/{id}", userChat.getId())
                        .content(objectMapper.writeValueAsString(userChat))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteTest() throws Exception {
        UserChat userChat = userChatRepository.save(buildUserChat());

        mockMvc.perform(MockMvcRequestBuilders.delete(ROOT + "/" + userChat.getId()))
                .andExpect(status().isNoContent());

        assertThat(userChatRepository.existsById(userChat.getId())).isFalse();
    }

    @Test
    void deleteTestWhenUserChatNotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete(ROOT + "/" + 1L))
                .andExpect(status().isNotFound());
    }

    @Test
    void getUserChatStatusTest() throws Exception{
        UserChat userChat = userChatRepository.save(buildUserChat());

        mockMvc.perform(MockMvcRequestBuilders.get(ROOT + "/" + userChat.getId() + "/status"))
                .andExpect(status().isOk())
                .andExpect(result -> {
                    StatusUserChat actual = objectMapper.readValue(result.getResponse().getContentAsString(), StatusUserChat.class);
                    assertThat(actual).isEqualTo(userChat.getStatusUserChat());
                });
    }

    @Test
    void getUserChatStatusWhenUserNotFoundTest() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get(ROOT + "/" + 1L + "/status"))
                .andExpect(status().isNotFound());
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
