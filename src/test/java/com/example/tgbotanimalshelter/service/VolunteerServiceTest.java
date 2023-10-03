package com.example.tgbotanimalshelter.service;

import com.example.tgbotanimalshelter.entity.Volunteer;
import com.example.tgbotanimalshelter.exception.VolunteerNotFoundException;
import com.example.tgbotanimalshelter.repository.VolunteerRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static com.example.tgbotanimalshelter.factory.VolunteerTestFactory.buildVolunteer;
import static com.example.tgbotanimalshelter.factory.VolunteerTestFactory.buildVolunteers;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class VolunteerServiceTest extends BaseServiceTest {

    @Autowired
    private VolunteerService volunteerService;

    @Autowired
    private VolunteerRepository volunteerRepository;

    @AfterEach
    void tearDown() {
        volunteerRepository.deleteAll();
    }

    @Test
    void findAllTest() {
        List<Volunteer> volunteers = volunteerRepository.saveAll(buildVolunteers());

        assertThat(volunteerService.findAll()).isEqualTo(volunteers);
        assertThat(volunteerService.findAll()).hasSize(volunteers.size());
    }

    @Test
    void findByIdTest() {
        Volunteer volunteer = volunteerRepository.save(buildVolunteer());

        assertThat(volunteerService.findById(volunteer.getChatId())).isEqualTo(volunteer);
        assertThat(volunteerRepository.existsById(volunteer.getChatId())).isTrue();
    }

    @Test
    void findByIdWhenVolunteerNotFoundTest() {
        assertThatExceptionOfType(VolunteerNotFoundException.class)
                .isThrownBy(() -> volunteerService.findById(1L));
    }
    @Test
    void createTest() {
        Volunteer volunteer = buildVolunteer();

        assertThat(volunteerService.create(volunteer))
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(volunteer);
    }

    @Test
    void updateTest() {
        Volunteer volunteer = volunteerRepository.save(buildVolunteer());

        assertThat(volunteerService.update(volunteer.getChatId(), volunteer)).isEqualTo(volunteer);
    }

    @Test
    void updateWhenVolunteerNotFoundTest() {
        Volunteer volunteer = buildVolunteer();

        assertThatExceptionOfType(VolunteerNotFoundException.class)
                .isThrownBy(() -> volunteerService.update(volunteer.getChatId(), volunteer));
    }

    @Test
    void deleteTest() {
        Volunteer volunteer = volunteerRepository.save(buildVolunteer());

        volunteerService.delete(volunteer.getChatId());

        assertThat(volunteerRepository.existsById(volunteer.getChatId())).isFalse();
    }

    @Test
    void deleteWhenVolunteerNotFoundTest() {
        assertThatExceptionOfType(VolunteerNotFoundException.class)
                .isThrownBy(() -> volunteerService.delete(1L));
    }
}