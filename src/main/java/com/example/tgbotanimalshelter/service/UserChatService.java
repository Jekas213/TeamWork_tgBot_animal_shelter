package com.example.tgbotanimalshelter.service;

import com.example.tgbotanimalshelter.entity.StatusUserChat;
import com.example.tgbotanimalshelter.entity.UserChat;
import com.example.tgbotanimalshelter.exception.UserNotFoundException;
import com.example.tgbotanimalshelter.repository.UserChatRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.example.tgbotanimalshelter.entity.StatusUserChat.*;

@Service
public class UserChatService implements CrudService<Long, UserChat>{

    private final UserChatRepository userChatRepository;

    public UserChatService(UserChatRepository userChatRepository) {
        this.userChatRepository = userChatRepository;
    }

    /**
     * If such a user is not in the repository, then it is saved to the repository<br>
     * The repository method is used {@link JpaRepository#findAll()}, {@link JpaRepository#save(Object)}
     *
     * @param chatId
     * @param userName
     */
    @Transactional
    public void editUserChat(long chatId, String userName) {
        UserChat userChat = new UserChat(chatId, userName, BASIC_STATUS);
        if (userChatRepository.findById(chatId).isEmpty()) {
            userChatRepository.save(userChat);
        }
    }

    public List<UserChat> findAll() {
        return userChatRepository.findAll();
    }

    @Override
    public UserChat findById(Long id) {
        return userChatRepository.findById(id).orElseThrow(UserNotFoundException::new);
    }

    @Transactional
    @Override
    public UserChat create(UserChat userChat) {
        return userChatRepository.save(userChat);
    }

    public StatusUserChat getUserChatStatus(long id) {
        return Optional.ofNullable(userChatRepository.findStatusUserChatById(id)).orElseThrow(UserNotFoundException::new);
    }

    @Transactional
    public void inviteWaitPhoneStatusDog(long chatId) {
        UserChat userChat = findById(chatId);
        userChat.setStatusUserChat(WAIT_FOR_NUMBER_DOG);
        userChatRepository.save(userChat);

    }
    @Transactional
    public void inviteWaitPhoneStatusCat(long chatId) {
        UserChat userChat = findById(chatId);
        userChat.setStatusUserChat(WAIT_FOR_NUMBER_CAT);
        userChatRepository.save(userChat);

    }

    public void inviteWaitReportStatus(long chatId) {
        UserChat userChat = findById(chatId).get();
        userChat.setStatusUserChat(WAIT_FOR_DIET);
        userChatRepository.save(userChat);

    }

    @Transactional
    public UserChat update(Long id, UserChat userChat) {
        if (userChatRepository.existsById(id)) {
            userChat.setId(id);
            userChatRepository.save(userChat);
            return userChat;
        }
        throw new UserNotFoundException();
    }

    @Transactional
    @Override
    public void delete(Long id) {
        if (userChatRepository.existsById(id)) {
            userChatRepository.deleteById(id);
            return;
        }
        throw new UserNotFoundException();
    }

}
