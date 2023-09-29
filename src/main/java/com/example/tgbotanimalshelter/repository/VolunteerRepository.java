package com.example.tgbotanimalshelter.repository;

import com.example.tgbotanimalshelter.entity.Volunteer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;



@Repository
public interface VolunteerRepository extends JpaRepository<Volunteer,Long> {
    @Query("select count(*) from Volunteer")
    long count();

}
