package com.example.tgbotanimalshelter.controller;

import com.example.tgbotanimalshelter.entity.CatParent;
import com.example.tgbotanimalshelter.service.CatParentService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * CatParentController class
 * author Savely
 */
@RestController
@RequestMapping("/catParent")
public class CatParentController extends CrudController<Long, CatParent> {

    private final CatParentService catParentService;

    public CatParentController(CatParentService catParentService) {
        super(catParentService);
        this.catParentService = catParentService;
    }
}
