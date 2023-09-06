package com.example.tgbotanimalshelter.service;

import com.example.tgbotanimalshelter.entity.Dog;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DogService implements CrudService<Long, Dog> {
    @Override
    public List<Dog> findAll() {
        return null;
    }

    @Override
    public Dog findById(Long id) {
        return null;
    }

    @Override
    public Dog create(Dog obj) {
        return null;
    }

    @Override
    public Dog update(Long id, Dog obj) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}
