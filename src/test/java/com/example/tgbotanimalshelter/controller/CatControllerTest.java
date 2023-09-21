package com.example.tgbotanimalshelter.controller;

import com.example.tgbotanimalshelter.entity.Cat;
import com.example.tgbotanimalshelter.exception.CatNotFoundException;
import com.example.tgbotanimalshelter.repository.CatRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CatControllerTest {
    private static final String ROOT = "/cat";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CatRepository catRepository;

    @Autowired
    private ObjectMapper objectMapper;


    @AfterEach
    void tearDown() {
        catRepository.deleteAll();
    }

    @Test
    void findAllTest() throws Exception {
        List<Cat> cats = catRepository.saveAll(buildCats());

        mockMvc.perform(get(ROOT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(result -> {
                    List<Cat> actual = objectMapper.readValue(result.getResponse().getContentAsString(),
                            new TypeReference<>() {
                            });

                    assertThat(actual).hasSize(cats.size());
                    assertThat(actual).isEqualTo(cats);
                });
    }

    @Test
    void findByIdTest() throws Exception {
        Cat cat = catRepository.save(buildCat());

        mockMvc.perform(get(ROOT + "/{id}", cat.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(result -> {
                    Cat actual = objectMapper.readValue(result.getResponse().getContentAsString(), Cat.class);

                    assertThat(actual).isEqualTo(cat);
                });
    }

    @Test
    void findByIdWhenCatNotFoundTest() throws Exception {
        mockMvc.perform(get(ROOT + "/{id}", 1L))
                .andExpect(status().isNotFound())
                .andExpect(result ->
                        assertThat(result.getResolvedException().getClass()).isEqualTo(CatNotFoundException.class));
    }

    @Test
    void createTest() throws Exception {
        Cat cat = buildCat();

        mockMvc.perform(post(ROOT)
                        .content(objectMapper.writeValueAsString(cat))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(result -> {
                    Cat actual = objectMapper.readValue(result.getResponse().getContentAsString(), Cat.class);

                    assertThat(actual)
                            .usingRecursiveComparison()
                            .ignoringFields("id")
                            .isEqualTo(cat);
                    assertThat(catRepository.existsById(actual.getId())).isTrue();
                });
    }

    @Test
    void updateTest() throws Exception {
        Cat cat = catRepository.save(buildCat());

        mockMvc.perform(put(ROOT + "/{id}", cat.getId())
                        .content(objectMapper.writeValueAsString(cat))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(result -> {
                    Cat actual = objectMapper.readValue(result.getResponse().getContentAsString(), Cat.class);

                    assertThat(actual).isEqualTo(cat);
                });
    }


    @Test
    void updateWhenCatNotFoundTest() throws Exception {
        Cat cat = buildCat();

        mockMvc.perform(put(ROOT + "/{id}", cat.getId())
                        .content(objectMapper.writeValueAsString(cat))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteTest() throws Exception {
        Cat cat = catRepository.save(buildCat());

        mockMvc.perform(MockMvcRequestBuilders.delete(ROOT + "/" + cat.getId()))
                .andExpect(status().isNoContent());

        assertThat(catRepository.existsById(cat.getId())).isFalse();
    }

    @Test
    void deleteTestWhenCatNotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete(ROOT + "/" + 1L))
                .andExpect(status().isNotFound());
    }

    private Cat buildCat() {
        return new Cat(0L, "cat", LocalDate.now(), "desc", true);
    }

    private List<Cat> buildCats(int count) {
        return LongStream.range(1, count)
                .mapToObj(i -> new Cat(0L,
                        "cat" + i, LocalDate.now().minusDays(i),
                        "desc" + i,
                        ThreadLocalRandom.current().nextBoolean()))
                .collect(Collectors.toList());
    }

    private List<Cat> buildCats() {
        return buildCats(10);
    }
}
