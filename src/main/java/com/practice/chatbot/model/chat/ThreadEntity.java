package com.practice.chatbot.model.chat;

import com.practice.chatbot.model.BaseDateAuditEntity;
import com.practice.chatbot.model.user.UserEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "thread") // 테이블 이름을 users로 설정
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ThreadEntity  extends BaseDateAuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(unique = true, nullable = false, updatable = false)
    private String id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @OneToMany(mappedBy = "thread", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ChatEntity> chatEntityList = new ArrayList<>();


    @Column(name = "thread_name")
    private String threadName;


    @Builder
    public ThreadEntity(String threadName, UserEntity user) {
        this.threadName = threadName;
        this.user = user;
    }
}
