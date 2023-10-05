package com.example.tgbotanimalshelter.service;

import com.example.tgbotanimalshelter.entity.Volunteer;
import com.example.tgbotanimalshelter.exception.VolunteerNotFoundException;
import com.example.tgbotanimalshelter.repository.VolunteerRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class VolunteerService implements CrudService<Long, Volunteer> {

    private final VolunteerRepository volunteerRepository;

    public VolunteerService(VolunteerRepository volunteerRepository) {
        this.volunteerRepository = volunteerRepository;
    }

    @Override
    public List<Volunteer> findAll() {
        return volunteerRepository.findAll();
    }

    @Override
    public Volunteer findById(Long id) {
        return volunteerRepository.findById(id).orElseThrow(VolunteerNotFoundException::new);
    }

    @Transactional
    @Override
    public Volunteer create(Volunteer volunteer) {
        return volunteerRepository.save(volunteer);
    }

    @Transactional
    @Override
    public Volunteer update(Long id, Volunteer volunteer) {
        if (volunteerRepository.existsById(id)) {
            volunteer.setChatId(id);
            return volunteerRepository.save(volunteer);
        }
        throw new VolunteerNotFoundException();
    }

    @Transactional
    @Override
    public void delete(Long id) {
        if (volunteerRepository.existsById(id)) {
            volunteerRepository.deleteById(id);
            return;
        }
        throw new VolunteerNotFoundException();
    }

    public long getRandomVolunteerId() {
        int random = getRandomNumber();
        return volunteerRepository.findFirstChatId(random)
                .orElseThrow(VolunteerNotFoundException::new);
    }

    private int getRandomNumber() {
        int count = volunteerRepository.findCountVolunteer();
        return ThreadLocalRandom.current().nextInt(0, count);
    }
}
