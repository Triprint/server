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
public class ReadTouristAttractionDto {
    @NonNull
    private Double x, y;

    @NonNull
    private String roadNameAddress;

    @NonNull
    private String touristAttraction;
}
