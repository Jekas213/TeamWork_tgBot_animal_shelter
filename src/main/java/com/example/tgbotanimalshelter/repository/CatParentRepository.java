package com.example.tgbotanimalshelter.repository;

import com.example.tgbotanimalshelter.entity.CatParent;
import com.example.tgbotanimalshelter.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CatParentRepository extends JpaRepository<CatParent, Long> {
    @Query("select p.status from CatParent p where p.chatId = :id")
    Status findStatusCatParentById(@Param("id") long id);
}
