package com.example.tgbotanimalshelter.service;

import com.example.tgbotanimalshelter.entity.CatParent;
import com.example.tgbotanimalshelter.entity.Status;
import com.example.tgbotanimalshelter.entity.StatusUserChat;
import com.example.tgbotanimalshelter.entity.UserChat;
import com.example.tgbotanimalshelter.exception.CatParentNotFoundException;
import com.example.tgbotanimalshelter.repository.CatParentRepository;
import com.example.tgbotanimalshelter.repository.CatRepository;
import com.example.tgbotanimalshelter.repository.UserChatRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import static com.example.tgbotanimalshelter.factory.CatTestFactory.buildCat;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class CatParentServiceTest extends BaseServiceTest {

    @Autowired
    private CatParentService catParentService;

    @Autowired
    private CatRepository catRepository;

    @Autowired
    private CatParentRepository catParentRepository;

    @Autowired
    private UserChatRepository userChatRepository;

    @AfterEach
    void tearDown() {
        catParentRepository.deleteAll();
        catRepository.deleteAll();
        userChatRepository.deleteAll();
    }

    @Test
    void findAllTest() {
        List<CatParent> catParents = catParentRepository.saveAll(buildCatsParents());

        assertThat(catParentService.findAll()).isEqualTo(catParents);
        assertThat(catParentService.findAll()).hasSize(catParents.size());
    }

    @Test
    void findByIdTest() {
        CatParent catParent = catParentRepository.save(buildCatParent());

        assertThat(catParentService.findById(catParent.getChatId())).isEqualTo(catParent);
        assertThat(catParentRepository.existsById(catParent.getChatId())).isTrue();
    }

    @Test
    void findByIdWhenCatParentNotFoundTest() {
        assertThatExceptionOfType(CatParentNotFoundException.class)
                .isThrownBy(() -> catParentService.findById(1L));
    }

    @Test
    void createTest() {
        CatParent catParent = buildCatParent();

        CatParent createdCat = catParentService.create(catParent);

        assertThat(createdCat)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(catParent);
    }

    @Test
    void updateTest() {
        CatParent catParent = catParentRepository.save(buildCatParent());

        CatParent updatedCat = catParentService.update(catParent.getChatId(), catParent);

        assertThat(updatedCat).isEqualTo(catParent);
    }

    @Test
    void updateWhenCatParentNotFoundTest() {
        CatParent catParent = buildCatParent();

        assertThatExceptionOfType(CatParentNotFoundException.class)
                .isThrownBy(() -> catParentService.update(catParent.getChatId(), catParent));
    }


    @Test
    void deleteTestWhenCatParentNotFoundTest() {
        assertThatExceptionOfType(CatParentNotFoundException.class)
                .isThrownBy(() -> catParentService.delete(1L));
    }

    @Test
    void deleteTest() {
        CatParent catParent = catParentRepository.save(buildCatParent());

        catParentService.delete(catParent.getChatId());

        assertThat(catParentRepository.existsById(catParent.getChatId())).isFalse();
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