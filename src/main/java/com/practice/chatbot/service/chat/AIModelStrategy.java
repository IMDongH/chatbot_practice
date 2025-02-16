package com.practice.chatbot.service.chat;

import com.practice.chatbot.service.chat.AIModelRequest.Message;
import java.util.List;

public interface AIModelStrategy {
    AIModelResponse.Message generateResponse(String prompt, String model, List<Message> prevMessages);
}