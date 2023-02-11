package com.triprint.backend.domain.auth.security;

import com.triprint.backend.domain.auth.security.oauth2.exception.TokenValidFailedException;
import com.triprint.backend.domain.user.entity.User;
import com.triprint.backend.domain.user.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Slf4j
public class TokenProvider {

    private final Key key;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;
    private static final String AUTHORITIES_KEY = "role";

    public TokenProvider(String secret) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
    }

    public AuthToken createAuthToken(Long id, Date expiry) {
        return new AuthToken(id, expiry, key);
    }

    public AuthToken createAuthToken(Long id, String role, Date expiry) { //어떤 토큰인지 식별필요
        return new AuthToken(id, role, expiry, key);
    }

    public AuthToken convertAuthToken(String token) {
        return new AuthToken(token, key);
    }

    public UsernamePasswordAuthenticationToken getAuthentication(AuthToken authToken) {

        if(authToken.validate()) {

            Claims claims = authToken.getTokenClaims();
//            Collection<? extends GrantedAuthority> authorities =
//                    Arrays.stream(new String[]{claims.get(AUTHORITIES_KEY).toString()})
//                            .map(SimpleGrantedAuthority::new)
//                            .collect(Collectors.toList());

            log.debug("claims subject := [{}]", claims.getSubject());

            UserPrincipal principal = customUserDetailsService.loadUserById(Long.parseLong(claims.getSubject()));

            return new UsernamePasswordAuthenticationToken(principal, authToken, principal.getAuthorities());
        } else {
            throw new TokenValidFailedException();
        }
    }

}