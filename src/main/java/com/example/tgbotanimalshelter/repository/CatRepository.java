package com.example.tgbotanimalshelter.repository;

import com.example.tgbotanimalshelter.entity.Cat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CatRepository extends JpaRepository<Cat, Long> {
}
