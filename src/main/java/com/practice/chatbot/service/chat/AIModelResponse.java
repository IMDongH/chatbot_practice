package com.practice.chatbot.service.chat;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
public class AIModelResponse {
    private List<Choice> choices;

    @Data
    public static class Choice {
        private int index;
        private Message message;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Message {
        private String role;
        private String content;
    }

}