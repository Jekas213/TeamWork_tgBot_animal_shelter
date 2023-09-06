package com.example.tgbotanimalshelter.service;

import com.example.tgbotanimalshelter.entity.Cat;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CatService implements CrudService<Long, Cat> {
    @Override
    public List<Cat> findAll() {
        return null;
    }

    @Override
    public Cat findById(Long id) {
        return null;
    }

    @Override
    public Cat create(Cat obj) {
        return null;
    }

    @Override
    public Cat update(Long id, Cat obj) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}
