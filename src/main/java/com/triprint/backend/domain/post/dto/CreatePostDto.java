package com.triprint.backend.domain.post.dto;

import com.triprint.backend.domain.post.entity.PostHashtag;
import com.triprint.backend.domain.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreatePostDto {
    private String title;
    private String content;
    private List<String> hashtag = new ArrayList();
}
