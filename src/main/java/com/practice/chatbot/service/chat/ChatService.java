package com.practice.chatbot.service.chat;


import com.practice.chatbot.exception.user.UserNotFoundException;
import com.practice.chatbot.model.chat.ChatEntity;
import com.practice.chatbot.model.chat.ThreadEntity;
import com.practice.chatbot.model.user.UserEntity;
import com.practice.chatbot.repository.chat.ChatRepository;
import com.practice.chatbot.repository.chat.ThreadRepository;
import com.practice.chatbot.repository.user.UserRepository;
import com.practice.chatbot.web.controller.chat.request.ChatRequest;
import com.practice.chatbot.web.controller.chat.response.ChatResponse;
import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ChatService {

    private final ChatRepository chatRepository;
    private final ThreadRepository threadRepository;
    private final UserRepository userRepository;
    private final Map<String, AIModelStrategy> strategies;

    @Transactional
    public ChatResponse createChat(ChatRequest request, String userId) {

//        - 스레드 생성 시점
//        - 해당 유저의 첫 질문이거나, 마지막 질문 후 30분이 지난 시점에 질문할 경우 생성합니다.
//        - 해당 유저가 마지막 질문으로부터 30분 이내에 다시 질문할 경우 기존 스레드가 유지됩니다.

        UserEntity user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);

        //추후 확작성 고려
        AIModelStrategy model = strategies.get("openAI");

        Optional<ChatEntity> lastChatOpt = chatRepository.findLastChatByUserId(userId);

        ThreadEntity thread = getThread(request, lastChatOpt, user);

        ChatEntity userChat = chatRepository.save(
            ChatEntity.createUserQuestion(request.getQuestion(), user, thread));

        List<AIModelRequest.Message> messageList = thread.getChatEntityList().stream()
            .map(chat -> new AIModelRequest.Message(chat.getQuestionRole(), chat.getContent()))
            .collect(Collectors.toList());
        AIModelResponse.Message message = model.generateResponse(request.getQuestion(), request.getModel(),messageList
            );

        ChatEntity modelChat = chatRepository.save(
            ChatEntity.createAIResponse(message.getContent(), user, thread));


        return ChatResponse.builder()
            .answer(modelChat.getContent())
            .threadId(thread.getId()).build();
    }

    private ThreadEntity getThread(ChatRequest request, Optional<ChatEntity> lastChatOpt,
        UserEntity user) {
        ThreadEntity thread;

        if (lastChatOpt.isEmpty()) {
            // 첫 질문인 경우 새로운 스레드 생성
            thread = threadRepository.save(
                ThreadEntity.builder()
                    .user(user)
                    .threadName(request.getQuestion())
                    .build()
            );
        } else {
            ChatEntity lastChat = lastChatOpt.get();
            OffsetDateTime lastChatTime = lastChat.getCreatedAt();
            OffsetDateTime now = OffsetDateTime.now();

            // 30분이 지나면 새로운 스레드 생성
            if (Duration.between(lastChatTime, now).toMinutes() > 30) {
                thread = threadRepository.save(
                    ThreadEntity.builder()
                        .user(user)
                        .threadName(request.getQuestion())
                        .build()
                );
            } else {
                thread = lastChat.getThread(); // 기존 스레드 유지
            }
        }
        return thread;
    }


}
