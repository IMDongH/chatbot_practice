package com.practice.chatbot.repository.chat;

import com.practice.chatbot.model.chat.ThreadEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ThreadRepository extends JpaRepository<ThreadEntity, String> {

}
