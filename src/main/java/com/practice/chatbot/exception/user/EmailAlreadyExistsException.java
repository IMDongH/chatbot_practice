package com.practice.chatbot.exception.user;

import static java.lang.String.format;

public class EmailAlreadyExistsException extends RuntimeException {

    public EmailAlreadyExistsException() {
        super("이미 존재하는 이메일 입니다.");
    }
}
