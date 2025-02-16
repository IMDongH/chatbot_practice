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
    public UserInfo(String id, String name, String email, String role) {
        this.email = email;
        this.id = id;
        this.name = name;
        this.role = role;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
            "email='" + email + '\'' +
            ", id='" + id + '\'' +
            ", name='" + name + '\'' +
            ", role='" + role + '\'' +
            '}';
    }
}
