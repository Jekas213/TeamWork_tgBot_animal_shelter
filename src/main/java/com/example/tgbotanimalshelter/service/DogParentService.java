package com.example.tgbotanimalshelter.service;

import com.example.tgbotanimalshelter.entity.DogParent;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DogParentService implements CrudService<Long, DogParent> {
    @Override
    public List<DogParent> findAll() {
        return null;
    }

    @Override
    public DogParent findById(Long id) {
        return null;
    }

    @Override
    public DogParent create(DogParent obj) {
        return null;
    }

    @Override
    public DogParent update(Long id, DogParent obj) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}
