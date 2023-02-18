package com.triprint.backend.domain.auth.security.controller;

import com.triprint.backend.domain.auth.security.common.ApiResponse;
import com.triprint.backend.domain.auth.security.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @GetMapping("/refresh")
    public ApiResponse refreshToken (HttpServletRequest request, HttpServletResponse response){
        return authService.refresh(request, response);
    }

}
