package com.example.tgbotanimalshelter.controller;

import com.example.tgbotanimalshelter.entity.Dog;
import com.example.tgbotanimalshelter.exception.DogNotFoundException;
import com.example.tgbotanimalshelter.repository.DogRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static com.example.tgbotanimalshelter.factory.DogTestFactory.buildDog;
import static com.example.tgbotanimalshelter.factory.DogTestFactory.buildDogs;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class DogControllerTest extends BaseControllerTest {
    private static final String ROOT = "/dog";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DogRepository dogRepository;

    @Autowired
    private ObjectMapper objectMapper;


    @AfterEach
    void tearDown() {
        dogRepository.deleteAll();
    }

    @Test
    void findAllTest() throws Exception {
        List<Dog> dogs = dogRepository.saveAll(buildDogs());

        mockMvc.perform(get(ROOT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(result -> {
                    List<Dog> actual = objectMapper.readValue(result.getResponse().getContentAsString(),
                            new TypeReference<>() {
                            });

                    assertThat(actual).hasSize(dogs.size());
                    assertThat(actual).isEqualTo(dogs);
                });
    }

    @Test
    void findByIdTest() throws Exception {
        Dog dog = dogRepository.save(buildDog());

        mockMvc.perform(get(ROOT + "/{id}", dog.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(result -> {
                    Dog actual = objectMapper.readValue(result.getResponse().getContentAsString(), Dog.class);

                    assertThat(actual).isEqualTo(dog);
                });
    }

    @Test
    void findByIdWhenDogNotFoundTest() throws Exception {
        mockMvc.perform(get(ROOT + "/{id}", 1L))
                .andExpect(status().isNotFound())
                .andExpect(result ->
                        assertThat(result.getResolvedException().getClass()).isEqualTo(DogNotFoundException.class));
    }

    @Test
    void createTest() throws Exception {
        Dog dog = buildDog();

        mockMvc.perform(post(ROOT)
                        .content(objectMapper.writeValueAsString(dog))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(result -> {
                    Dog actual = objectMapper.readValue(result.getResponse().getContentAsString(), Dog.class);

                    assertThat(actual)
                            .usingRecursiveComparison()
                            .ignoringFields("id")
                            .isEqualTo(dog);
                    assertThat(dogRepository.existsById(actual.getId())).isTrue();
                });
    }

    @Test
    void updateTest() throws Exception {
        Dog dog = dogRepository.save(buildDog());

        mockMvc.perform(put(ROOT + "/{id}", dog.getId())
                        .content(objectMapper.writeValueAsString(dog))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(result -> {
                    Dog actual = objectMapper.readValue(result.getResponse().getContentAsString(), Dog.class);

                    assertThat(actual).isEqualTo(dog);
                });
    }


    @Test
    void updateWhenDogNotFoundTest() throws Exception {
        Dog dog = buildDog();

        mockMvc.perform(put(ROOT + "/{id}", dog.getId())
                        .content(objectMapper.writeValueAsString(dog))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteTest() throws Exception {
        Dog dog = dogRepository.save(buildDog());

        mockMvc.perform(MockMvcRequestBuilders.delete(ROOT + "/" + dog.getId()))
                .andExpect(status().isNoContent());

        assertThat(dogRepository.existsById(dog.getId())).isFalse();
    }

    @Test
    void deleteTestWhenDogNotFoundTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete(ROOT + "/" + 1L))
                .andExpect(status().isNotFound());
    }
}
