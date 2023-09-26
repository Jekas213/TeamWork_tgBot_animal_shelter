package com.example.tgbotanimalshelter.service;

import com.example.tgbotanimalshelter.entity.DogParent;
import com.example.tgbotanimalshelter.exception.DogParentNotFoundException;
import com.example.tgbotanimalshelter.repository.DogParentRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class DogParentService implements CrudService<Long, DogParent> {

    private final DogParentRepository dogParentRepository;

    public DogParentService(DogParentRepository dogParentRepository) {
        this.dogParentRepository = dogParentRepository;
    }

    @Override
    public List<DogParent> findAll() {
        return dogParentRepository.findAll();
    }

    @Override
    public DogParent findById(Long id) {
        return dogParentRepository.findById(id).orElseThrow(DogParentNotFoundException::new);
    }

    @Transactional
    @Override
    public DogParent create(DogParent dogParent) {
        return dogParentRepository.save(dogParent);
    }

    @Transactional
    @Override
    public DogParent update(Long id, DogParent dogParent) {
        if (dogParentRepository.existsById(id)) {
            dogParent.setChatId(id);
            dogParentRepository.save(dogParent);
            return dogParent;
        }
        throw new DogParentNotFoundException();
    }

    @Transactional
    @Override
    public void delete(Long id) {
        if (dogParentRepository.existsById(id)) {
            dogParentRepository.deleteById(id);
            return;
        }
        throw new DogParentNotFoundException();

    }
}
