package com.triprint.backend.domain.user.config.jwt;

import org.springframework.beans.factory.annotation.Value;

public interface JwtProperties {

//    @Value("${token.secret-key}")
//    String SECRET;
    String TOKEN_SECRET_KEY = "TRIPrINT";

//    @Value("${token.expiration-time}")
//    int TIME = 1; //60000 1분 //864000000 10일
    int EXPIRATION_TIME = 1728000000;

    String TOKEN_PREFIX = "Bearer ";
    String HEADER_STRING = "Authorization";
}
