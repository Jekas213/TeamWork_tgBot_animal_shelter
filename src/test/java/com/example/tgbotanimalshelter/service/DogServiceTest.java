package com.example.tgbotanimalshelter.service;

import com.example.tgbotanimalshelter.entity.Dog;
import com.example.tgbotanimalshelter.exception.DogNotFoundException;
import com.example.tgbotanimalshelter.repository.DogRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static com.example.tgbotanimalshelter.factory.DogTestFactory.buildDog;
import static com.example.tgbotanimalshelter.factory.DogTestFactory.buildDogs;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class DogServiceTest extends BaseServiceTest {

    @Autowired
    private DogRepository dogRepository;

    @Autowired
    private DogService dogService;

    @AfterEach
    void tearDown() {
        dogRepository.deleteAll();
    }

    @Test
    void findAllTest() {
        List<Dog> dogs = dogRepository.saveAll(buildDogs());

        assertThat(dogService.findAll()).isEqualTo(dogs);
        assertThat(dogRepository.findAll()).hasSize(dogs.size());
    }

    @Test
    void findByIdTest() {
        Dog dog = dogRepository.save(buildDog());

        assertThat(dogService.findById(dog.getId())).isEqualTo(dog);
    }

    @Test
    void findByIdWhenDogNotFoundTest() {
        assertThatExceptionOfType(DogNotFoundException.class)
                .isThrownBy(() -> dogService.findById(1L));
    }

    @Test
    void createTest() {
        Dog dog = buildDog();

        Dog createdDog = dogService.create(dog);

        assertThat(dogService.findById(createdDog.getId()))
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(dog);
        assertThat(dogRepository.existsById(createdDog.getId())).isTrue();
    }

    @Test
    void updateTest() {
        Dog dog = dogRepository.save(buildDog());

        Dog updatedDog = dogService.update(dog.getId(), dog);

        assertThat(updatedDog).isEqualTo(dog);
    }

    @Test
    void updateWhenDogNotFoundTest() {
        Dog dog = buildDog();

        assertThatExceptionOfType(DogNotFoundException.class)
                .isThrownBy(() -> dogService.update(dog.getId(), dog));
    }

    @Test
    void deleteTest() {
        Dog dog = dogRepository.save(buildDog());

        dogService.delete(dog.getId());

        assertThat(dogRepository.existsById(dog.getId())).isFalse();
    }

    @Test
    void deleteTestWhenDogNotFoundTest() {
        assertThatExceptionOfType(DogNotFoundException.class)
                .isThrownBy(() -> dogService.delete(1L));
    }
}