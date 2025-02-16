package com.practice.chatbot.web.controller.chat;

import com.practice.chatbot.model.chat.ThreadEntity;
import com.practice.chatbot.security.annotation.JwtAuthorization;
import com.practice.chatbot.security.vo.UserInfo;
import com.practice.chatbot.service.chat.ChatService;
import com.practice.chatbot.web.controller.chat.request.ChatRequest;
import com.practice.chatbot.web.controller.chat.response.ChatResponse;
import com.practice.chatbot.web.controller.chat.response.ThreadResponse;
import com.practice.chatbot.web.vo.PageRequest;
import com.practice.chatbot.web.vo.PageResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/v1/chats")
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    @PostMapping("/ask")
    public ResponseEntity<ChatResponse> askQuestion(@RequestBody ChatRequest request,
        @JwtAuthorization UserInfo userInfo) {

        log.info(userInfo.toString());
        return ResponseEntity.ok(chatService.createChat(request, userInfo.getId()));
    }

    @GetMapping
    public ResponseEntity<PageResponse<ThreadResponse>> getThread(
        @JwtAuthorization UserInfo userInfo,
        PageRequest pageRequest) {
        return ResponseEntity.ok(chatService.readThread(userInfo.getId(),pageRequest.of()));

    }

    @GetMapping("/thread/{threadId}")
    public ResponseEntity<Page<ThreadResponse>> getChatList(
        @JwtAuthorization UserInfo userInfo,
        @PathVariable String threadId) {

//        chatService.readChat(userInfo.getId(),threadId)
        return null;
    }
}
