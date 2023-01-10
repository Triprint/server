package com.triprint.backend.domain.post.dto;

import com.triprint.backend.domain.image.entity.Image;
import com.triprint.backend.domain.user.entity.User;
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
    private ArrayList<String> images;
    private ArrayList<String> hashtag;
    private Timestamp createdAt;
}
