package com.triprint.backend.domain.post.service;

import com.amazonaws.util.CollectionUtils;
import com.triprint.backend.domain.image.entity.Image;
import com.triprint.backend.domain.image.repository.ImageRepository;
import com.triprint.backend.domain.post.dto.CreatePostDto;
import com.triprint.backend.domain.post.entity.Post;
import com.triprint.backend.domain.post.repository.PostRepository;
import com.triprint.backend.domain.user.entity.User;
import com.triprint.backend.domain.user.service.AwsS3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final ImageRepository imageRepository;
    private final AwsS3Service awsS3Service;

    @Transactional
    public Long create(User writer, CreatePostDto createPostDto, List<MultipartFile> images) throws IllegalStateException, Exception{

        List<Image> imageList = new ArrayList<>();
//        if(CollectionUtils.isEmpty(images)){
//
//        }
        for (MultipartFile uploadImg : images){
            String imageUrl = awsS3Service.uploadFile("posts", uploadImg);
            Image image = new Image(imageUrl);
            imageList.add(image);
            imageRepository.save(image);
        }
        Post post = Post.builder()
                .user(writer)
                .title(createPostDto.getTitle())
                .contents(createPostDto.getContent())
                .images(imageList).build();

        return postRepository.save(post).getId();
    }

}
