package com.triprint.backend.domain.auth.security.controller;

import com.triprint.backend.domain.auth.security.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @GetMapping("/refresh")
    public ResponseEntity refreshToken (HttpServletRequest request, HttpServletResponse response){
        return new ResponseEntity<>(authService.refresh(request, response), HttpStatus.CREATED);
    }

}
