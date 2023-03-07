package com.triprint.backend.domain.location.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotNull;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReadTouristAttractionDto {
    @NotNull
    private Double x, y;

    @NotNull
    private String roadNameAddress;

    @NotNull
    private String name;
}
