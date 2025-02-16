package com.practice.chatbot.security.vo;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserInfo {
    private String id;
    private String email;
    private String name;
    private String role;

    @Builder
    public UserInfo(String email, String id, String name, String role) {
        this.email = email;
        this.id = id;
        this.name = name;
        this.role = role;
    }
}
