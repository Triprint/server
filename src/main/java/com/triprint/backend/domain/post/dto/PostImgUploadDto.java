package com.triprint.backend.domain.post.dto;

import com.triprint.backend.domain.image.entity.Image;

import java.util.ArrayList;
import java.util.List;

public class PostImgUploadDto {
    private Long postid;
    private List<Image> imageList;
}
