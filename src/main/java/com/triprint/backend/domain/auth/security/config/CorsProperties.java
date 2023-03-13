package com.triprint.backend.domain.auth.security.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Service
@ConfigurationProperties(prefix = "cors")
public class CorsProperties {
	private String allowedOrigins;
	private String allowedMethods;
	private String allowedHeaders;
	private Long maxAge;
}
