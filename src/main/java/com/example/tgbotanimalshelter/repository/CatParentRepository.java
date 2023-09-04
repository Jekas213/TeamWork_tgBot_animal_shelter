package com.example.tgbotanimalshelter.repository;

import com.example.tgbotanimalshelter.entity.CatParent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CatParentRepository extends JpaRepository<CatParent, Long> {

}
