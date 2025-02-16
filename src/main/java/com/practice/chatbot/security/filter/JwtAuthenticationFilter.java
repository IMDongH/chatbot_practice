package com.practice.chatbot.security.filter;

import com.practice.chatbot.security.JwtTokenProvider;
import com.practice.chatbot.security.vo.JwtAuthenticationToken;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;

    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
        HttpServletResponse response,
        FilterChain filterChain) throws ServletException, IOException {

        String token = extractToken(request);

        if (token != null) {
            try {
                Claims claims = jwtTokenProvider.extractClaims(token);
                String email = claims.getSubject();

                UserDetails userDetails = User.builder()
                    .username(email)
                    .password("") // JWT 인증에서는 비밀번호 필요 없음
                    .roles(claims.get("role", String.class))
                    .build();

                log.info("Extracted Claims: {}", claims);

                JwtAuthenticationToken authenticationToken =
                    new JwtAuthenticationToken(userDetails, token, claims, userDetails.getAuthorities());

                SecurityContextHolder.getContext().setAuthentication(authenticationToken);

                log.info("SecurityContext Authentication Set: {}", SecurityContextHolder.getContext().getAuthentication());

            } catch (JwtException e) {
                log.error("JWT 검증 실패: {}", e.getMessage());
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid JWT token");
                return;
            }
        }

        filterChain.doFilter(request, response);
    }

    private String extractToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7);
        }
        return null;
    }
}