package com.triprint.backend.domain.location.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateTouristAttractionDto {
    @NonNull
    private String x, y;

    @NonNull
    private String roadNameAddress;

    @NonNull
    private String name;
}
