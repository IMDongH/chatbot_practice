package com.practice.chatbot.model.user;

import com.practice.chatbot.model.user.enums.Role;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Entity
@Table(name = "users") // 테이블 이름을 users로 설정
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(unique = true, nullable = false, updatable = false)
    private String id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    private String refreshToken;

    @Builder
    private UserEntity(String email, String username, String password, Role role) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public static UserEntity createMember(String email, String username, String encodedPassword) {
        return UserEntity.builder()
            .email(email)
            .username(username)
            .password(encodedPassword)
            .role(Role.MEMBER)
            .build();
    }
}