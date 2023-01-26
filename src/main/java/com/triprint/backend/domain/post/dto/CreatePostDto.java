package com.triprint.backend.domain.post.dto;

import com.sun.istack.Nullable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreatePostDto {
    @Nullable
    private String title;
    @Nullable
    private String content;
    @Nullable
    private List<String> hashtag = new ArrayList();
    private Long x, y;
    private String roadNameAddress;
    private String firstDepthName;
    private String secondDepthName;
}
