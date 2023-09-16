package com.example.tgbotanimalshelter.controller;

import com.example.tgbotanimalshelter.service.CrudService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Abstract CrudController class
 *
 * @param <I> Id
 * @param <T> Object type
 *            author Savely
 */
public abstract class CrudController<I, T> {

    private final CrudService<I, T> crudService;

    protected CrudController(CrudService<I, T> crudService) {
        this.crudService = crudService;
    }

    @GetMapping
    public ResponseEntity<List<T>> findAll() {
        return ResponseEntity.ok(crudService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<T> findById(@PathVariable I id) {
        return ResponseEntity.ok(crudService.findById(id));
    }

    @PostMapping
    public ResponseEntity<T> create(@RequestBody T obj) {
        return ResponseEntity.status(HttpStatus.CREATED).body(crudService.create(obj));
    }

    @PutMapping("/{id}")
    public ResponseEntity<T> update(@PathVariable I id, @RequestBody T obj) {
        return ResponseEntity.ok(crudService.update(id, obj));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable I id) {
        crudService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
