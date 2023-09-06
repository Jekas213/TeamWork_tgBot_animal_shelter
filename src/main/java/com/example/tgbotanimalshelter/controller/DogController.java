package com.example.tgbotanimalshelter.controller;

import com.example.tgbotanimalshelter.entity.Dog;
import com.example.tgbotanimalshelter.service.DogService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * DogController class
 * author Savely
 */
@RestController
@RequestMapping("/dog")
public class DogController extends CrudController<Long, Dog> {

    private final DogService dogService;

    public DogController(DogService dogService) {
        super(dogService);
        this.dogService = dogService;
    }
}
