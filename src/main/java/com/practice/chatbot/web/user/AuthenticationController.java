package com.practice.chatbot.web.user;

import com.practice.chatbot.service.user.AuthenticationService;
import com.practice.chatbot.web.user.request.SignInRequest;
import com.practice.chatbot.web.user.request.SignupRequest;
import com.practice.chatbot.web.user.response.SignInResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/authentication")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/signup")
    public ResponseEntity<Void> signup(
        @RequestBody @Valid SignupRequest request
    ) {
        authenticationService.signupMember(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/signin")
    public ResponseEntity<SignInResponse> signIn(@RequestBody SignInRequest request
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(authenticationService.signInMember(request));
    }
}
