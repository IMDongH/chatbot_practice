package com.practice.chatbot.service.user;

import com.practice.chatbot.exception.user.EmailAlreadyExistsException;
import com.practice.chatbot.exception.user.PasswordInvalidException;
import com.practice.chatbot.exception.user.UserNotFoundException;
import com.practice.chatbot.model.user.UserEntity;
import com.practice.chatbot.repository.UserRepository;
import com.practice.chatbot.web.user.request.SignupRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final UserValidator userValidator;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void signupMember(SignupRequest request) {

        if (userValidator.invokeEmailUnique(request.getEmail())) {
            throw new EmailAlreadyExistsException();
        }

        if (userValidator.invokePasswordValid(request.getPassword())) {
            throw new PasswordInvalidException();
        }

        String encodedPassword = passwordEncoder.encode(request.getPassword());

        userRepository.save(
            UserEntity.createMember(request.getEmail(), request.getUsername(), encodedPassword));
    }

}
