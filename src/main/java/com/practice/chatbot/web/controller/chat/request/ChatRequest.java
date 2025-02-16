package com.practice.chatbot.web.controller.chat.request;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatRequest {
    private String question;
    private Boolean isStreaming;
    private String model;

    @Builder
    public ChatRequest(Boolean isStreaming, String model, String question) {
        this.isStreaming = isStreaming;
        this.model = model;
        this.question = question;
    }
}
