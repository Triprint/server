package com.triprint.backend.domain.post.dto;

import com.triprint.backend.domain.location.dto.ReadTouristAttractionDto;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;
import lombok.Builder;
import lombok.Getter;
import org.springframework.lang.NonNull;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReadPostDto {
    private String authorName;
    @Nullable
    private String title;
    @Nullable
    private String content;
    @Nullable
    private List<String> images = new ArrayList<>();
    @Nullable
    private List<String> hashtags = new ArrayList<>();
    private Timestamp createdAt;
    private Timestamp updatedAt;
    @NonNull
    private ReadTouristAttractionDto touristAttraction;
}
