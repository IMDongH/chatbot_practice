package com.practice.chatbot.exception.user;

public class PasswordInvalidException extends RuntimeException {

    public PasswordInvalidException() {
        super("잘못된 비밀번호 형식 입니다.");
    }
}
