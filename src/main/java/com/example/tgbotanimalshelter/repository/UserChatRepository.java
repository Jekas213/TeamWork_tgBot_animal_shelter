package com.example.tgbotanimalshelter.repository;

import com.example.tgbotanimalshelter.entity.StatusUserChat;
import com.example.tgbotanimalshelter.entity.UserChat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserChatRepository extends JpaRepository<UserChat, Long> {
    @Query("select statusUserChat from UserChat where id = :id")
    StatusUserChat findStatusUserChatById(@Param("id") long id);

}
