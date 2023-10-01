package com.example.tgbotanimalshelter.service;

import com.example.tgbotanimalshelter.entity.Cat;
import com.example.tgbotanimalshelter.exception.CatNotFoundException;
import com.example.tgbotanimalshelter.repository.CatRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static com.example.tgbotanimalshelter.factory.CatTestFactory.buildCat;
import static com.example.tgbotanimalshelter.factory.CatTestFactory.buildCats;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class CatServiceTest extends BaseServiceTest {

    @Autowired
    private CatRepository catRepository;

    @Autowired
    private CatService catService;

    @AfterEach
    void tearDown() {
        catRepository.deleteAll();
    }

    @Test
    void findAllTest() {
        List<Cat> cats = catRepository.saveAll(buildCats());

        assertThat(catService.findAll()).isEqualTo(cats);
        assertThat(catService.findAll()).hasSize(cats.size());
    }

    @Test
    void findByIdTest() {
        Cat cat = catRepository.save(buildCat());

        assertThat(catService.findById(cat.getId())).isEqualTo(cat);
    }

    @Test
    void findByIdWhenCatNotFoundTest() {
        assertThatExceptionOfType(CatNotFoundException.class)
                .isThrownBy(() -> catService.findById(1L));
    }

    @Test
    void createTest() {
        Cat cat = buildCat();

        Cat createdCat = catService.create(cat);

        assertThat(createdCat)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(cat);
        assertThat(catRepository.existsById(createdCat.getId())).isTrue();
    }

    @Test
    void updateTest() {
        Cat cat = catRepository.save(buildCat());

        Cat updatedCat = catService.update(cat.getId(), cat);

        assertThat(updatedCat).isEqualTo(cat);
    }

    @Test
    void updateWhenCatNotFoundTest() {
        Cat cat = buildCat();

        assertThatExceptionOfType(CatNotFoundException.class)
                .isThrownBy(() -> catService.update(cat.getId(), cat));
    }

    @Test
    void delete() {
        Cat cat = catRepository.save(buildCat());

        catService.delete(cat.getId());

        assertThat(catRepository.existsById(cat.getId())).isFalse();
    }

    @Test
    void deleteTestWhenCatNotFound() {
        assertThatExceptionOfType(CatNotFoundException.class)
                .isThrownBy(() -> catService.delete(1L));
    }

}