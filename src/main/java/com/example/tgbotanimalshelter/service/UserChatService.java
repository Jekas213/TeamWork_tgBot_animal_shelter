package com.example.tgbotanimalshelter.service;

import com.example.tgbotanimalshelter.entity.UserChat;
import com.example.tgbotanimalshelter.repository.UserChatRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserChatService {

    private final UserChatRepository userChatRepository;

    public UserChatService(UserChatRepository userChatRepository) {
        this.userChatRepository = userChatRepository;
    }

    /**
     * If such a user is not in the repository, then it is saved to the repository<br>
     * The repository method is used {@link JpaRepository#findAll()}, {@link JpaRepository#save(Object)}
     * @param chatId
     * @param userName
     */
    public void editUserChat(long chatId, String userName) {
        UserChat userChat = new UserChat(chatId, userName);
        if (!findAll().contains(userChat)) {
            userChatRepository.save(userChat);
        }
    }

    public List<UserChat> findAll() {
        return userChatRepository.findAll();
    }

}
