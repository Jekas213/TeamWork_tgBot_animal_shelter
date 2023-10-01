package com.example.tgbotanimalshelter.service;

import com.example.tgbotanimalshelter.entity.CatParent;
import com.example.tgbotanimalshelter.exception.CatParentNotFoundException;
import com.example.tgbotanimalshelter.repository.CatParentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CatParentService implements CrudService<Long, CatParent> {
    private final CatParentRepository catParentRepository;

    @Override
    public List<CatParent> findAll() {
        return catParentRepository.findAll();
    }

    @Override
    public CatParent findById(Long id) {
        return catParentRepository.findById(id).orElseThrow(CatParentNotFoundException::new);
    }

    @Transactional
    @Override
    public CatParent create(CatParent catParent) {
        return catParentRepository.save(catParent);
    }

    @Transactional
    @Override
    public CatParent update(Long id, CatParent catParent) {
        if (catParentRepository.existsById(id)) {
            catParent.setChatId(id);
            catParentRepository.save(catParent);
            return catParent;
        }
        throw new CatParentNotFoundException();
    }

    @Transactional
    @Override
    public void delete(Long id) {
        if (catParentRepository.existsById(id)) {
            catParentRepository.deleteById(id);
            return;
        }
        throw new CatParentNotFoundException();
    }
}
