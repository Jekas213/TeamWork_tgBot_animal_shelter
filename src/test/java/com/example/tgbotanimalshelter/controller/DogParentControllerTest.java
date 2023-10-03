package com.example.tgbotanimalshelter.controller;

import com.example.tgbotanimalshelter.entity.DogParent;
import com.example.tgbotanimalshelter.entity.Status;
import com.example.tgbotanimalshelter.entity.StatusUserChat;
import com.example.tgbotanimalshelter.entity.UserChat;
import com.example.tgbotanimalshelter.exception.DogParentNotFoundException;
import com.example.tgbotanimalshelter.repository.DogParentRepository;
import com.example.tgbotanimalshelter.repository.DogRepository;
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
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import static com.example.tgbotanimalshelter.factory.DogTestFactory.buildDog;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class DogParentControllerTest extends BaseControllerTest {

    private static final String ROOT = "/dogParent";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DogParentRepository dogParentRepository;

    @Autowired
    private DogRepository dogRepository;

    @Autowired
    private UserChatRepository userChatRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @AfterEach
    void tearDown() {
        dogParentRepository.deleteAll();
        dogRepository.deleteAll();
        userChatRepository.deleteAll();
    }

    @Test
    void findAllTest() throws Exception {
        List<DogParent> dogsParents = dogParentRepository.saveAll(buildDogsParents());

        mockMvc.perform(get(ROOT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(result -> {
                    List<DogParent> actual = objectMapper.readValue(result.getResponse().getContentAsString(),
                            new TypeReference<>() {
                            });

                    assertThat(actual).hasSize(dogsParents.size());
                    assertThat(actual).isEqualTo(dogsParents);
                });
    }

    @Test
    void findByIdTest() throws Exception {
        DogParent dogParent = dogParentRepository.save(buildDogParent());

        mockMvc.perform(get(ROOT + "/{id}", dogParent.getChatId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(result -> {
                    DogParent actual = objectMapper.readValue(result.getResponse().getContentAsString(), DogParent.class);

                    assertThat(actual).isEqualTo(dogParent);
                });
    }

    @Test
    void findByIdWhenDogParentNotFoundTest() throws Exception {
        mockMvc.perform(get(ROOT + "/{id}", 1L))
                .andExpect(status().isNotFound())
                .andExpect(result ->
                        assertThat(result.getResolvedException().getClass()).isEqualTo(DogParentNotFoundException.class));
    }

    @Test
    void createTest() throws Exception {
        DogParent dogParent = buildDogParent();

        mockMvc.perform(post(ROOT)
                        .content(objectMapper.writeValueAsString(dogParent))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(result -> {
                    DogParent actual = objectMapper.readValue(result.getResponse().getContentAsString(), DogParent.class);

                    assertThat(actual)
                            .usingRecursiveComparison()
                            .ignoringFields("id")
                            .isEqualTo(dogParent);
                    assertThat(dogParentRepository.existsById(actual.getChatId())).isTrue();
                });
    }

    @Test
    void updateTest() throws Exception {
        DogParent dogParent = dogParentRepository.save(buildDogParent());

        mockMvc.perform(put(ROOT + "/{id}", dogParent.getChatId())
                        .content(objectMapper.writeValueAsString(dogParent))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(result -> {
                    DogParent actual = objectMapper.readValue(result.getResponse().getContentAsString(), DogParent.class);

                    assertThat(actual).isEqualTo(dogParent);
                });
    }

    @Test
    void updateWhenDogParentNotFoundTest() throws Exception {
        DogParent dogParent = buildDogParent();

        mockMvc.perform(put(ROOT + "/{id}", dogParent.getChatId())
                        .content(objectMapper.writeValueAsString(dogParent))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteTest() throws Exception {
        DogParent dogParent = dogParentRepository.save(buildDogParent());

        mockMvc.perform(MockMvcRequestBuilders.delete(ROOT + "/" + dogParent.getChatId()))
                .andExpect(status().isNoContent());

        assertThat(dogParentRepository.existsById(dogParent.getChatId())).isFalse();
    }

    @Test
    void deleteTestWhenDogParentNotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete(ROOT + "/" + 1L))
                .andExpect(status().isNotFound());
    }

    private DogParent buildDogParent() {
        return new DogParent(
                userChatRepository.save(new UserChat(1L, "userChat", "name", StatusUserChat.BASIC_STATUS)).getId(),
                "fullName",
                "9000000000",
                "address",
                Status.SEARCH,
                dogRepository.save(buildDog())
        );
    }

    private List<DogParent> buildDogsParents(int count) {
        return LongStream.range(1, count)
                .mapToObj(i -> new DogParent(
                        userChatRepository.save(new UserChat(i, "name" + i, "userChat" + i, StatusUserChat.BASIC_STATUS)).getId(),
                        "fullName" + i,
                        "900000000" + i,
                        "address" + i,
                        Status.values()[ThreadLocalRandom.current().nextInt(Status.values().length)],
                        dogRepository.save(buildDog())))
                .collect(Collectors.toList());
    }

    private List<DogParent> buildDogsParents() {
        return buildDogsParents(10);
    }
}
