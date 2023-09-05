package com.example.tgbotanimalshelter.repository;

import com.example.tgbotanimalshelter.entity.DogShelter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DogShelterRepository extends JpaRepository<DogShelter, Long> {

}
