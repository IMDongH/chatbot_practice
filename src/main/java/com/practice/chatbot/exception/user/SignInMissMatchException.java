package com.practice.chatbot.exception.user;

public class SignInMissMatchException extends RuntimeException {

    public SignInMissMatchException() {
        super("이메일 또는 비밀번호가 올바르지 않습니다.");
    }
}
