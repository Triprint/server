package com.triprint.backend.domain.post.dto;

import com.sun.istack.Nullable;
import com.triprint.backend.domain.location.dto.CreateTouristAttractionDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import javax.validation.constraints.NotNull;

import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UpdatePostDto {
    @Nullable
    @Size(min = 0, max = 50)
    private String title;
    @Nullable
    @Size(min = 0, max = 500)
    private String contents;
    @Nullable
    private List<String> hashtag = new ArrayList();
    @Nullable
    private List<String> existentImages = new ArrayList<>();
    @NotNull
    private CreateTouristAttractionDto touristAttraction;
}
