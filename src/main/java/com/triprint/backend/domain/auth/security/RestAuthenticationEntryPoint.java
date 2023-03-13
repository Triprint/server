package com.triprint.backend.domain.auth.security;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {

	private static final Logger logger = LoggerFactory.getLogger(RestAuthenticationEntryPoint.class);

	@Override
	public void commence(HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse,
		AuthenticationException ex) throws IOException {
		logger.error("Responding with unauthorized error. Message - {}", ex.getMessage());
		httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED,
			ex.getLocalizedMessage());
	}
}
