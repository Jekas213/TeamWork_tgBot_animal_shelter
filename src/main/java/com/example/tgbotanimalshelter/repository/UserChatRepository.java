package com.example.tgbotanimalshelter.repository;

import com.example.tgbotanimalshelter.entity.UserChat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserChatRepository extends JpaRepository<UserChat, Long> {

}
