package com.triprint.backend.domain.post.dto;

import com.triprint.backend.domain.hashtag.entity.Hashtag;
import lombok.Builder;
import lombok.Getter;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
public class ReadPostDto {
    private String authorName;
    private String title;
    private String content;
    private List<String> images = new ArrayList<>();
    private List<String> hashtags = new ArrayList<>();
    private Timestamp createdAt;
    private Timestamp updatedAt;
}
