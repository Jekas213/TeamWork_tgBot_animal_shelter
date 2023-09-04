package com.example.tgbotanimalshelter.repository;

import com.example.tgbotanimalshelter.entity.CatShelter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CatShelterRepository extends JpaRepository<CatShelter, Long> {

}
