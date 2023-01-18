package com.triprint.backend.domain.post.dto;

import com.sun.istack.Nullable;
import lombok.Builder;
import lombok.Getter;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
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
}
