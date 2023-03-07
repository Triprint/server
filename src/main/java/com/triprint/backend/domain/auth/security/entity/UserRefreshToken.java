package com.triprint.backend.domain.auth.security.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRefreshToken {
	@JsonIgnore
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long refreshTokenSeq;

	@NotNull
	private Long userId;

	@NotNull
	private String refreshToken;

	@Builder
	public UserRefreshToken(@NotNull Long userId, @NotNull String refreshToken) {
		this.userId = userId;
		this.refreshToken = refreshToken;
	}
}
