package com.example.tgbotanimalshelter.repository;

import com.example.tgbotanimalshelter.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {

    List<Report> findByChatId(Long chatId);

    @Query("""
            SELECT p.picture
            FROM Report p
            WHERE p.chatId = :chatId
            AND p.id = :id
            """)
    Optional<byte[]> findPictureByChatId(@Param("chatId") Long chatId,
                                     @Param("id") Long id);
}
