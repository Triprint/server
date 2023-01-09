package com.triprint.backend.domain.post.service;

import com.amazonaws.util.CollectionUtils;
import com.triprint.backend.domain.image.entity.Image;
import com.triprint.backend.domain.image.repository.ImageRepository;
import com.triprint.backend.domain.post.dto.CreatePostDto;
import com.triprint.backend.domain.post.dto.ReadPostDto;
import com.triprint.backend.domain.post.entity.Post;
import com.triprint.backend.domain.post.repository.PostRepository;
import com.triprint.backend.domain.user.entity.User;
import com.triprint.backend.domain.user.service.AwsS3Service;
import com.triprint.backend.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final ImageRepository imageRepository;
    private final AwsS3Service awsS3Service;
    private final UserService userService;

    @Transactional
    public Long create(HttpServletRequest request, CreatePostDto createPostDto, List<MultipartFile> images) throws IllegalStateException, Exception {
        Long userId = (Long) request.getAttribute("userId");
        User author = userService.findById(userId);

        Post post = Post.builder()
                .author(author)
                .title(createPostDto.getTitle())
                .contents(createPostDto.getContent())
                .build();
        images.forEach((img) -> {
            String image = awsS3Service.uploadFile("posts", img);
            post.addImage(imageRepository.save(Image.builder().path(image).build()));
//            createPostDto.getImages().add(image);
        });
        return postRepository.save(post).getId();
    }

    public ReadPostDto read(Long postId){
        Post post = postRepository.findByid(postId);

        ArrayList<String> images = new ArrayList<>();

        for (Image img : post.getImages()){
            images.add(img.getPath());
        }

        return ReadPostDto.builder()
                .authorName(post.getAuthor().getUsername())
                .title(post.getTitle())
                .content(post.getContents())
                .createdAt(post.getCreatedAt())
                .images(images).build();
    }

}
