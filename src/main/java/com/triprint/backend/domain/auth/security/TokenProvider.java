package com.triprint.backend.domain.auth.security;

import com.triprint.backend.core.config.AppProperties;
import com.triprint.backend.domain.auth.security.oauth2.exception.TokenValidFailedException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.security.Key;
import java.util.Date;

@Slf4j
public class TokenProvider {

    private final Key key;
    private final Key refreshKey;
    private final AppProperties appProperties;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;
    private static final String AUTHORITIES_KEY = "role";

    public TokenProvider(AppProperties appProperties) {
        this.key = Keys.hmacShaKeyFor(appProperties.getAuth().getTokenSecret().getBytes());
        this.refreshKey = Keys.hmacShaKeyFor(appProperties.getAuth().getRefreshTokenSecret().getBytes());
        this.appProperties = appProperties;
    }

    public AuthToken createRefreshToken(Long id) {
        return new AuthToken(id, appProperties.getAuth().getRefreshTokenExpiration(), refreshKey);
    }

    public AuthToken createAccessToken(Long id, String role) {
        return new AuthToken(id, role, appProperties.getAuth().getTokenExpiration(), key);
    }

    public AuthToken convertAuthToken(String token) {
        return new AuthToken(token, key);
    }

    public AuthToken convertRefreshToken(String token) {
        return new AuthToken(token, refreshKey);
    }

    public UsernamePasswordAuthenticationToken getAuthentication(AuthToken authToken) {

        if(authToken.validate()) {

            Claims claims = authToken.getTokenClaims();
            log.debug("claims subject := [{}]", claims.getSubject());

            UserPrincipal principal = customUserDetailsService.loadUserById(Long.parseLong(claims.getSubject()));

            return new UsernamePasswordAuthenticationToken(principal, authToken, principal.getAuthorities());
        } else {
            throw new TokenValidFailedException();
        }
    }

}