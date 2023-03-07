package com.triprint.backend.domain.auth.security.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.triprint.backend.domain.auth.security.service.AuthService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
	private final AuthService authService;

	@GetMapping("/refresh")
	@PreAuthorize("hasRole('ROLE_USER')")
	public ResponseEntity refreshToken(HttpServletRequest request, HttpServletResponse response) {
		return new ResponseEntity<>(authService.refresh(request, response), HttpStatus.CREATED);
	}

}
