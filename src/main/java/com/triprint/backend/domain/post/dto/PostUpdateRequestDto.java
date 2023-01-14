package com.triprint.backend.domain.post.dto;

import com.triprint.backend.domain.hashtag.entity.Hashtag;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import javax.lang.model.type.UnionType;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PostUpdateRequestDto {
    private String title;
    private String contents;
    private List<String> hashtag = new ArrayList();
    private List<String> existentImages = new ArrayList<>();
}
