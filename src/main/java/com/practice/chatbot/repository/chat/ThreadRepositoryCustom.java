package com.practice.chatbot.repository.chat;


import com.practice.chatbot.model.chat.ThreadEntity;
import com.practice.chatbot.web.controller.chat.response.ThreadResponse;
import com.practice.chatbot.web.vo.PageResponse;
import org.springframework.data.domain.Pageable;

public interface ThreadRepositoryCustom {
    PageResponse<ThreadResponse> findByUserIdWithPagination(String userId, Pageable pageable);
}