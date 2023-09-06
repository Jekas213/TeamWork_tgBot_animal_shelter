package com.example.tgbotanimalshelter.controller;

import com.example.tgbotanimalshelter.entity.Cat;
import com.example.tgbotanimalshelter.service.CatService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * CatController class
 * author Savely
 */
@RestController
@RequestMapping("/cat")
public class CatController extends CrudController<Long, Cat> {

    private final CatService catService;

    public CatController(CatService catService) {
        super(catService);
        this.catService = catService;
    }
}
