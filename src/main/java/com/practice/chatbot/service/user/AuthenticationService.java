package com.practice.chatbot.service.user;

import com.practice.chatbot.exception.user.EmailAlreadyExistsException;
import com.practice.chatbot.exception.user.PasswordInvalidException;
import com.practice.chatbot.exception.user.SignInMissMatchException;
import com.practice.chatbot.model.user.UserEntity;
import com.practice.chatbot.repository.user.UserRepository;
import com.practice.chatbot.repository.user.UserTokenRepository;
import com.practice.chatbot.web.user.request.SignInRequest;
import com.practice.chatbot.web.user.request.SignupRequest;
import com.practice.chatbot.web.user.response.SignInResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final UserTokenRepository userTokenRepository;
    private final UserValidator userValidator;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
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

    @Transactional
    public SignInResponse signInMember(SignInRequest request) {

        UserEntity user = userRepository.findByEmail(request.getEmail())
            .orElseThrow(SignInMissMatchException::new);

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new SignInMissMatchException();
        }

        String accessToken = jwtUtil.generateAccessToken(user.getEmail(), user.getRole().name());
        String refreshToken = jwtUtil.generateRefreshToken(user.getEmail());

        userTokenRepository.updateRefreshToken(user.getId(),refreshToken);

        return SignInResponse.create(user.getId(),user.getEmail(),user.getUsername(),accessToken);
    }
}
