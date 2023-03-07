package com.triprint.backend.domain.post.dto;

import com.triprint.backend.domain.location.dto.CreateTouristAttractionDto;
import javax.validation.constraints.NotNull;
import org.springframework.lang.Nullable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreatePostDto {
    @Nullable
    @Size(min = 0, max = 50)
    private String title;

    @Nullable
    @Size(min = 0, max = 500)
    private String content;

    @Nullable
    private List<String> hashtag = new ArrayList();

    @NotNull
    private CreateTouristAttractionDto touristAttraction;
}
