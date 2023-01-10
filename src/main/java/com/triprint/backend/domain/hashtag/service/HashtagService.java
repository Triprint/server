package com.triprint.backend.domain.hashtag.service;

import com.triprint.backend.domain.hashtag.entity.Hashtag;
import com.triprint.backend.domain.hashtag.repository.HashtagRepository;
import com.triprint.backend.domain.post.entity.Post;
import com.triprint.backend.domain.post.entity.PostHashtag;
import com.triprint.backend.domain.post.repository.PostHashtagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class HashtagService {
    private final HashtagRepository hashtagRepository;
    private final PostHashtagRepository postHashtagRepository;

    public void createPosthashtag(Post post, List<String> hashtags){
        hashtags.forEach((hashtag) -> {
            Hashtag tag = this.findOrCreate(hashtag);
            PostHashtag postHashtag = PostHashtag.builder()
                            .post(post)
                            .hashtag(tag)
                            .build();
            postHashtagRepository.save(postHashtag);
        });
        return;
    }

    public Hashtag findOrCreate(String hashtag){
        return hashtagRepository.findBycontents(hashtag).orElseGet(() -> this.createHashtag(hashtag));
    }

    public Hashtag createHashtag(String hashtag){
        return hashtagRepository.save(Hashtag.builder().contents(hashtag).build());
    }

}
