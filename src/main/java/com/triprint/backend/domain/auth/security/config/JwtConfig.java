package com.triprint.backend.domain.auth.security.config;

import com.triprint.backend.domain.auth.security.TokenProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtConfig {
    @Value("${jwt.secret}")
    private String secret;

    @Bean
    public TokenProvider jwtProvider() {
        return new TokenProvider(secret);
    }
}
