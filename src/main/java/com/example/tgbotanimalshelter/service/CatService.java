package com.example.tgbotanimalshelter.service;

import com.example.tgbotanimalshelter.entity.Cat;
import com.example.tgbotanimalshelter.exception.CatNotFoundException;
import com.example.tgbotanimalshelter.repository.CatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CatService implements CrudService<Long, Cat> {

    private final CatRepository catRepository;

    @Override
    public List<Cat> findAll() {
        return catRepository.findAll();
    }

    @Override
    public Cat findById(Long id) {
        return catRepository.findById(id).orElseThrow(CatNotFoundException::new);
    }

    @Transactional
    @Override
    public Cat create(Cat cat) {
        return catRepository.save(cat);
    }

    @Transactional
    @Override
    public Cat update(Long id, Cat cat) {
        if (catRepository.existsById(id)) {
            cat.setId(id);
            catRepository.save(cat);
            return cat;
        }
        throw new CatNotFoundException();
    }

    @Transactional
    @Override
    public void delete(Long id) {
        if (catRepository.existsById(id)) {
            catRepository.deleteById(id);
            return;
        }
        throw new CatNotFoundException();
    }
}
