package com.example.tgbotanimalshelter.service;

import java.util.List;

/**
 * Abstract CrudService class
 *
 * @param <I> Id
 * @param <T> Object type
 */
public interface CrudService<I, T> {
    List<T> findAll();

    T findById(I id);

    T create(T obj);

    T update(I id, T obj);

    void delete(I id);
}
