package com.example.tgbotanimalshelter.service;

import com.example.tgbotanimalshelter.entity.CatParent;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CatParentService implements CrudService<Long, CatParent> {
    @Override
    public List<CatParent> findAll() {
        return null;
    }

    @Override
    public CatParent findById(Long id) {
        return null;
    }

    @Override
    public CatParent create(CatParent obj) {
        return null;
    }

    @Override
    public CatParent update(Long id, CatParent obj) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}
