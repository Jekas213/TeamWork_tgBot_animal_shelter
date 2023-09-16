package com.example.tgbotanimalshelter.controller;

import com.example.tgbotanimalshelter.entity.DogParent;
import com.example.tgbotanimalshelter.service.DogParentService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * DogParentController class
 * author Savely
 */
@RestController
@RequestMapping("/dogParent")
public class DogParentController extends CrudController<Long, DogParent> {

    private final DogParentService dogParentService;

    public DogParentController(DogParentService dogParentService) {
        super(dogParentService);
        this.dogParentService = dogParentService;
    }
}
