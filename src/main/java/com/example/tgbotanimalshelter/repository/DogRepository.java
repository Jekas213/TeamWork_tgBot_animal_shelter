package com.example.tgbotanimalshelter.repository;

import com.example.tgbotanimalshelter.entity.Dog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DogRepository extends JpaRepository<Dog, Long> {
}
