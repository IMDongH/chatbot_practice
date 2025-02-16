package com.practice.chatbot.service.chat;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AIModelRequest {
    private String model;
    private List<Message> messages;

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Message {
        private String role;
        private String content;
    }

    @Builder
    public AIModelRequest(List<Message> prevMessages,String message, String model) {
        prevMessages.add(new AIModelRequest.Message("user",message));
        this.messages = prevMessages;
        this.model = model;
    }
}