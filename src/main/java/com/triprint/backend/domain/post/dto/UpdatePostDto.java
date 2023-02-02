package com.triprint.backend.domain.post.dto;

import com.sun.istack.Nullable;
import com.triprint.backend.domain.location.dto.CreateTouristAttractionDto;
import com.triprint.backend.domain.location.dto.ReadTouristAttractionDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.lang.NonNull;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UpdatePostDto {
    @Nullable
    private String title;
    @Nullable
    private String contents;
    @Nullable
    private List<String> hashtag = new ArrayList();
    @Nullable
    private List<String> existentImages = new ArrayList<>();
    @NonNull
    private CreateTouristAttractionDto touristAttraction;
}
