package com.example.tgbotanimalshelter.controller;

import com.example.tgbotanimalshelter.entity.CatParent;
import com.example.tgbotanimalshelter.entity.Status;
import com.example.tgbotanimalshelter.entity.StatusUserChat;
import com.example.tgbotanimalshelter.entity.UserChat;
import com.example.tgbotanimalshelter.exception.CatParentNotFoundException;
import com.example.tgbotanimalshelter.repository.CatParentRepository;
import com.example.tgbotanimalshelter.repository.CatRepository;
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

import static com.example.tgbotanimalshelter.factory.CatTestFactory.buildCat;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CatParentControllerTest extends BaseControllerTest{

    private static final String ROOT = "/catParent";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CatParentRepository catParentRepository;

    @Autowired
    private CatRepository catRepository;

    @Autowired
    private UserChatRepository userChatRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @AfterEach
    void tearDown() {
        catParentRepository.deleteAll();
        catRepository.deleteAll();
        userChatRepository.deleteAll();
    }

    @Test
    void findAllTest() throws Exception {
        List<CatParent> catParents = catParentRepository.saveAll(buildCatsParents());

        mockMvc.perform(get(ROOT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(result -> {
                    List<CatParent> actual = objectMapper.readValue(result.getResponse().getContentAsString(),
                            new TypeReference<>() {
                            });

                    assertThat(actual).hasSize(catParents.size());
                    assertThat(actual).isEqualTo(catParents);
                });
    }

    @Test
    void findByIdTest() throws Exception {
        CatParent catParent = catParentRepository.save(buildCatParent());

        mockMvc.perform(get(ROOT + "/{id}", catParent.getChatId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(result -> {
                    CatParent actual = objectMapper.readValue(result.getResponse().getContentAsString(), CatParent.class);

                    assertThat(actual).isEqualTo(catParent);
                });
    }

    @Test
    void findByIdWhenCatParentNotFoundTest() throws Exception {
        mockMvc.perform(get(ROOT + "/{id}", 1L))
                .andExpect(status().isNotFound())
                .andExpect(result ->
                        assertThat(result.getResolvedException().getClass()).isEqualTo(CatParentNotFoundException.class));
    }

    @Test
    void createTest() throws Exception {
        CatParent catParent = buildCatParent();

        mockMvc.perform(post(ROOT)
                        .content(objectMapper.writeValueAsString(catParent))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(result -> {
                    CatParent actual = objectMapper.readValue(result.getResponse().getContentAsString(), CatParent.class);

                    assertThat(actual)
                            .usingRecursiveComparison()
                            .ignoringFields("id")
                            .isEqualTo(catParent);
                    assertThat(catParentRepository.existsById(actual.getChatId())).isTrue();
                });
    }

    @Test
    void updateTest() throws Exception {
        CatParent catParent = catParentRepository.save(buildCatParent());

        mockMvc.perform(put(ROOT + "/{id}", catParent.getChatId())
                        .content(objectMapper.writeValueAsString(catParent))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(result -> {
                    CatParent actual = objectMapper.readValue(result.getResponse().getContentAsString(), CatParent.class);

                    assertThat(actual).isEqualTo(catParent);
                });
    }

    @Test
    void updateWhenCatParentNotFoundTest() throws Exception {
        CatParent catParent = buildCatParent();

        mockMvc.perform(put(ROOT + "/{id}", catParent.getChatId())
                        .content(objectMapper.writeValueAsString(catParent))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteTest() throws Exception {
        CatParent catParent = catParentRepository.save(buildCatParent());

        mockMvc.perform(MockMvcRequestBuilders.delete(ROOT + "/" + catParent.getChatId()))
                .andExpect(status().isNoContent());

        assertThat(catParentRepository.existsById(catParent.getChatId())).isFalse();
    }

    @Test
    void deleteTestWhenCatParentNotFoundTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete(ROOT + "/" + 1L))
                .andExpect(status().isNotFound());
    }

    private CatParent buildCatParent() {
        return new CatParent(
                userChatRepository.save(new UserChat(1L, "name", "userChat", StatusUserChat.BASIC_STATUS)).getId(),
                "fullName",
                "9000000000",
                "address",
                Status.SEARCH,
                catRepository.save(buildCat())
        );
    }

    private List<CatParent> buildCatsParents(int count) {
        return LongStream.range(1, count)
                .mapToObj(i -> new CatParent(
                        userChatRepository.save(new UserChat(i, "name" + i, "userChat" + i, StatusUserChat.BASIC_STATUS)).getId(),
                        "fullName" + i,
                        "900000000" + i,
                        "address" + i,
                        Status.values()[ThreadLocalRandom.current().nextInt(Status.values().length)],
                        catRepository.save(buildCat())))
                .collect(Collectors.toList());
    }

    private List<CatParent> buildCatsParents() {
        return buildCatsParents(10);
    }
}
