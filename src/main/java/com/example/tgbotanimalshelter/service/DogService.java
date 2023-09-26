package com.example.tgbotanimalshelter.service;

import com.example.tgbotanimalshelter.entity.Dog;
import com.example.tgbotanimalshelter.exception.DogNotFoundException;
import com.example.tgbotanimalshelter.repository.DogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DogService implements CrudService<Long, Dog> {

    private final DogRepository dogRepository;

    @Override
    public List<Dog> findAll() {
        return dogRepository.findAll();
    }

    @Override
    public Dog findById(Long id) {
        return dogRepository.findById(id).orElseThrow(DogNotFoundException::new);
    }

    @Transactional
    @Override
    public Dog create(Dog dog) {
        return dogRepository.save(dog);
    }

    @Transactional
    @Override
    public Dog update(Long id, Dog dog) {
        if (dogRepository.existsById(id)) {
            dog.setId(id);
            dogRepository.save(dog);
            return dog;
        }
        throw new DogNotFoundException();
    }

    @Transactional
    @Override
    public void delete(Long id) {
        if (dogRepository.existsById(id)) {
            dogRepository.deleteById(id);
            return;
        }
        throw new DogNotFoundException();

    }
}
