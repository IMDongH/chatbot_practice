package com.practice.chatbot.vo.user;

import java.util.Collection;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


public class JwtAuthenticationToken extends AbstractAuthenticationToken {

    private final UserDetails principal;
    private final String token;

    public JwtAuthenticationToken(UserDetails principal, String token, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        this.token = token;
        setAuthenticated(true); // 인증 성공 상태로 설정
    }

    @Override
    public Object getCredentials() {
        return token; // JWT 토큰이 비밀번호 역할
    }

    @Override
    public Object getPrincipal() {
        return principal; // 사용자 정보 반환
    }
}