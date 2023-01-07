package com.triprint.backend.domain.post.dto;

import lombok.Builder;

import java.util.Date;

public class Post {
    private Long fileNum;
    private String title;
    private String postImg;
    private String content;
    private String writer;
    private String hashtag;
    private Date regdate;

    @Builder
    public Post(String postImg, String title, String content, String writer){
        this.postImg = postImg;
        this.title = title;
        this.content = content;
        this.writer = writer;
    }
}
