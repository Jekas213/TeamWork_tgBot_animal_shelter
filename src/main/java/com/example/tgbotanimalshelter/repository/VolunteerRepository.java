package com.example.tgbotanimalshelter.repository;

import com.example.tgbotanimalshelter.entity.Volunteer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface VolunteerRepository extends JpaRepository<Volunteer, Long> {
    @Query(value = """
            SELECT chat_id
            FROM volunteer
            LIMIT 1 OFFSET :rdm
            """, nativeQuery = true)
    Optional<Long> findFirstChatId(int rdm);

    @Query(value = "select count (*) from Volunteer")
    int findCountVolunteer();
}
