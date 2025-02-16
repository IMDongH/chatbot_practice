package com.practice.chatbot.service.user;

import com.practice.chatbot.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserValidator {

    private final UserRepository userRepository;

    public boolean invokeEmailUnique(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

   public boolean invokePasswordValid(String password) {
        //정책 추가시 수정 예정
        return false;
   }
}
