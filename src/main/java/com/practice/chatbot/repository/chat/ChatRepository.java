package com.practice.chatbot.repository.chat;

import com.practice.chatbot.model.chat.ChatEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ChatRepository extends JpaRepository<ChatEntity, String> {

    @Query("SELECT c FROM ChatEntity c WHERE c.user.id = :userId and c.questionRole='user' ORDER BY c.id DESC LIMIT 1")
    Optional<ChatEntity> findLastChatByUserId(String userId);
}
