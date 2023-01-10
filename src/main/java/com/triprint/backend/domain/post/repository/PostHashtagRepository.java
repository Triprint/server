package com.triprint.backend.domain.post.repository;

import com.triprint.backend.domain.post.entity.PostHashtag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostHashtagRepository extends JpaRepository<PostHashtag, Long> {
//    PostHashtag savePostHashtag(Long hashtagId, Long postId);
}
