package com.practice.chatbot.service.chat.openAI;

import com.practice.chatbot.service.chat.AIModelRequest;
import com.practice.chatbot.service.chat.AIModelResponse;
import com.practice.chatbot.service.chat.AIModelStrategy;
import com.practice.chatbot.service.chat.AIModelRequest.Message;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service("openAI")
@RequiredArgsConstructor
@Slf4j
public class OpenAIService implements AIModelStrategy {

    private static final String OPENAI_API_URL = "https://api.openai.com/v1/chat/completions";

    private final RestTemplate openAiTemplate;


    @Override
    public AIModelResponse.Message generateResponse(String prompt, String model,
        List<Message> prevMessages) {
        AIModelRequest aiModelRequest = new AIModelRequest(
            prevMessages,
            prompt,
            model
        );
        log.info("AIModelRequest 생성: prompt={}, model={}, prevMessages={}",
            prompt, model, prevMessages);
        AIModelResponse AIModelResponse = openAiTemplate.postForObject(OPENAI_API_URL,
            aiModelRequest,
            AIModelResponse.class);
        return AIModelResponse.getChoices().getFirst().getMessage();

    }
}