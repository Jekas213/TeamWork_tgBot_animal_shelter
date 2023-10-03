package com.example.tgbotanimalshelter.repository;

import com.example.tgbotanimalshelter.entity.DogParent;
import com.example.tgbotanimalshelter.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DogParentRepository extends JpaRepository<DogParent, Long> {
    @Query("select status from DogParent where chatId = :id")
    Status findStatusDogParentById(@Param("id") long id);
}
