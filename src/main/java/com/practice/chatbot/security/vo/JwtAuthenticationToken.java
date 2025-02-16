package com.practice.chatbot.security.vo;

import io.jsonwebtoken.Claims;
import java.util.Collection;
import lombok.Getter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


public class JwtAuthenticationToken extends AbstractAuthenticationToken {

    private final UserDetails principal;
    private final String token;
    @Getter
    private final Claims claims;

    public JwtAuthenticationToken(UserDetails principal, String token, Claims claims, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        this.token = token;
        this.claims = claims;
        setAuthenticated(true); // 인증 성공 상태로 설정
    }

    @Override
    public Object getCredentials() {
        return token;
    }

    @Override
    public Object getPrincipal() {
        return principal;
    }

}