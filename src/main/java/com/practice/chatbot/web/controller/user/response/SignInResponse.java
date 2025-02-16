package com.practice.chatbot.web.controller.user.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SignInResponse {

    private String userId;
    private String email;
    private String username;
    private String token;

    public static SignInResponse create(String userId, String email, String username, String token) {
        return new SignInResponse(userId, email, username, token);
    }
}
