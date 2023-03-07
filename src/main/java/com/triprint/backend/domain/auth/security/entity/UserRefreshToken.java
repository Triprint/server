package com.triprint.backend.domain.auth.security.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.validation.constraints.NotNull;
import lombok.*;

import javax.persistence.*;

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
