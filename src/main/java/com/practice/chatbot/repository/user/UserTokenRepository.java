package com.practice.chatbot.repository.user;

import com.practice.chatbot.model.user.UserEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

//추후 redis 등 변경
public interface UserTokenRepository extends JpaRepository<UserEntity, String> {

    @Modifying
    @Transactional
    @Query("UPDATE UserEntity u SET u.refreshToken = :refreshToken WHERE u.id = :userId")
    void updateRefreshToken(String userId, String refreshToken);

}
