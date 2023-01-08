package com.triprint.backend.domain.post.service;

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
    public Long create(User writer, CreatePostDto createPostDto) throws Exception{

        List<Image> imageList = new ArrayList<>();
        for (MultipartFile uploadImg : createPostDto.getImages()){
            String imageUrl = awsS3Service.uploadFile("posts", uploadImg);
            Image image = new Image(imageUrl);
            imageList.add(image);
            imageRepository.save(image);
        }
        Post post = new Post(
                writer,
                createPostDto.getTitle(),
                createPostDto.getContent(),
                imageList
        );

        return postRepository.save(post).getId();
    }

}
