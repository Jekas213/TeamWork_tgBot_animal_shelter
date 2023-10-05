package com.example.tgbotanimalshelter.service;

import com.example.tgbotanimalshelter.entity.DogParent;
import com.example.tgbotanimalshelter.entity.Status;
import com.example.tgbotanimalshelter.entity.StatusUserChat;
import com.example.tgbotanimalshelter.entity.UserChat;
import com.example.tgbotanimalshelter.exception.DogParentNotFoundException;
import com.example.tgbotanimalshelter.repository.DogParentRepository;
import com.example.tgbotanimalshelter.repository.DogRepository;
import com.example.tgbotanimalshelter.repository.UserChatRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import static com.example.tgbotanimalshelter.factory.DogTestFactory.buildDog;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class DogParentServiceTest extends BaseServiceTest {

    @Autowired
    private DogRepository dogRepository;

    @Autowired
    private DogParentRepository dogParentRepository;

    @Autowired
    private DogParentService dogParentService;

    @Autowired
    private UserChatRepository userChatRepository;

    @AfterEach
    void tearDown() {
        dogParentRepository.deleteAll();
        dogRepository.deleteAll();
        userChatRepository.deleteAll();
    }

    @Test
    void findAllTest() {

        List<DogParent> dogParents = dogParentRepository.saveAll(buildDogsParents());

        assertThat(dogParentService.findAll()).isEqualTo(dogParents);
        assertThat(dogParentService.findAll()).hasSize(dogParents.size());
    }

    @Test
    void findByIdTest() {
        DogParent dogParent = dogParentRepository.save(buildDogParent());

        assertThat(dogParentService.findById(dogParent.getChatId())).isEqualTo(dogParent);
        assertThat(dogParentRepository.existsById(dogParent.getChatId())).isTrue();
    }


    @Test
    void findByIdWhenDogParentNotFoundTest() {
        assertThatExceptionOfType(DogParentNotFoundException.class)
                .isThrownBy(() -> dogParentService.findById(1L));
    }

    @Test
    void createTest() {
        DogParent dogParent = buildDogParent();

        DogParent createdDogParent = dogParentService.create(dogParent);

        assertThat(createdDogParent)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(dogParent);
    }

    @Test
    void updateTest() {
        DogParent dogParent = dogParentRepository.save(buildDogParent());

        DogParent updatedDogParent = dogParentService.update(dogParent.getChatId(), dogParent);

        assertThat(updatedDogParent).isEqualTo(dogParent);
    }

    @Test
    void updateWhenDogParentNotFoundTest() {
        DogParent dogParent = buildDogParent();

        assertThatExceptionOfType(DogParentNotFoundException.class)
                .isThrownBy(() -> dogParentService.update(dogParent.getChatId(), dogParent));
    }

    @Test
    void delete() {
        DogParent dogParent = dogParentRepository.save(buildDogParent());

        dogParentService.delete(dogParent.getChatId());

        assertThat(dogParentRepository.existsById(dogParent.getChatId())).isFalse();
    }

    @Test
    void deleteTestWhenDogParentNotFound() {
        assertThatExceptionOfType(DogParentNotFoundException.class)
                .isThrownBy(() -> dogParentService.delete(1L));
    }

    private DogParent buildDogParent() {
        return new DogParent(
                userChatRepository.save(new UserChat(1L, "userChat", "name", 1L, 1L, StatusUserChat.BASIC_STATUS)).getId(),
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
                        userChatRepository.save(new UserChat(i, "name" + i, "userChat" + i, i, i, StatusUserChat.BASIC_STATUS)).getId(),
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