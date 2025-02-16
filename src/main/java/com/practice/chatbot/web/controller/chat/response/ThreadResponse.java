package com.practice.chatbot.web.controller.chat.response;


import com.practice.chatbot.model.chat.ThreadEntity;
import java.time.OffsetDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
//@AllArgsConstructor
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ThreadResponse {

    private String id;
    private String threadName;
    private OffsetDateTime createAt;

    public static ThreadResponse of(ThreadEntity thread) {
        return ThreadResponse.builder()
            .id(thread.getId())
            .threadName(thread.getThreadName())
            .createAt(thread.getCreatedAt())
            .build();
    }
}
