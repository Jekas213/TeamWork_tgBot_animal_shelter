package com.example.tgbotanimalshelter.repository;

import com.example.tgbotanimalshelter.entity.DogParent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DogParentRepository extends JpaRepository<DogParent, Long> {

}
