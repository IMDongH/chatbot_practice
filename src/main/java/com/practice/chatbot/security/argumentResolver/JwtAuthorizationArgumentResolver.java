package com.practice.chatbot.security.argumentResolver;

import com.practice.chatbot.security.annotation.JwtAuthorization;
import com.practice.chatbot.security.vo.JwtAuthenticationToken;
import com.practice.chatbot.security.vo.UserInfo;
import org.springframework.core.MethodParameter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtAuthorizationArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterAnnotation(JwtAuthorization.class) != null
            && parameter.getParameterType().equals(UserInfo.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter,
        ModelAndViewContainer mavContainer,
        NativeWebRequest webRequest,
        WebDataBinderFactory binderFactory) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        log.info("SecurityContext Authentication: {}", authentication);
        log.info("SecurityContext Authentication Class: {}", authentication.getClass().getName());

        if (authentication instanceof JwtAuthenticationToken jwtToken) {
            log.info("Extracted Claims from Token: {}", jwtToken.getClaims());

            return new UserInfo(
                jwtToken.getClaims().get("id", String.class),
                jwtToken.getClaims().get("name", String.class),
                jwtToken.getClaims().getSubject(), // email
                jwtToken.getClaims().get("role", String.class)
            );
        }

        log.warn("JwtAuthenticationToken not found in SecurityContext");
        return null;
    }
}