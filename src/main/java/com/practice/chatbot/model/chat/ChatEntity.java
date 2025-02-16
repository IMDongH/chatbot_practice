package com.practice.chatbot.model.chat;

import com.practice.chatbot.model.BaseDateAuditEntity;
import com.practice.chatbot.model.user.UserEntity;
import com.practice.chatbot.service.chat.AIModelRequest.Message;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "chats") // 테이블 이름을 users로 설정
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatEntity extends BaseDateAuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(unique = true, nullable = false, updatable = false)
    private String id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "thread_id", nullable = false)
    private ThreadEntity thread;

    @Column(nullable = false, updatable = false)
    private String questionRole;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;


    @Builder
    public ChatEntity(String content, String questionRole, ThreadEntity thread, UserEntity user) {
        this.content = content;
        this.questionRole = questionRole;
        this.thread = thread;
        this.user = user;
    }

    public static ChatEntity createUserQuestion(String prompt, UserEntity user, ThreadEntity thread) {
        return ChatEntity.builder()
            .content(prompt)
            .user(user)
            .thread(thread)
            .questionRole("user")
            .build();
    }

    public static ChatEntity createAIResponse(String prompt, UserEntity user, ThreadEntity thread) {
        return ChatEntity.builder()
            .content(prompt)
            .user(user)
            .thread(thread)
            .questionRole("assistant")
            .build();
    }

    public static Message toMessage(ChatEntity chatEntity) {
        return new Message(chatEntity.questionRole,chatEntity.content);
    }
}
