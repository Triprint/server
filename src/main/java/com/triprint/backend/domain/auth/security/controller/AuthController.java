package com.triprint.backend.domain.auth.security.controller;

import com.triprint.backend.domain.auth.security.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @GetMapping("/refresh")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity refreshToken (HttpServletRequest request, HttpServletResponse response){
        return new ResponseEntity<>(authService.refresh(request, response), HttpStatus.CREATED);
    }

}
