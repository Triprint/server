package com.triprint.backend.domain.post.service;

import com.triprint.backend.domain.hashtag.service.HashtagService;
import com.triprint.backend.domain.image.entity.Image;
import com.triprint.backend.domain.image.repository.ImageRepository;
import com.triprint.backend.domain.location.Repository.TouristAttractionRepository;
import com.triprint.backend.domain.location.entity.TouristAttraction;
import com.triprint.backend.domain.location.service.TouristAttractionService;
import com.triprint.backend.domain.post.dto.CreatePostDto;
import com.triprint.backend.domain.post.dto.UpdatePostDto;
import com.triprint.backend.domain.post.dto.ReadPostDto;
import com.triprint.backend.domain.post.entity.Post;
import com.triprint.backend.domain.post.entity.PostHashtag;
import com.triprint.backend.domain.post.repository.PostRepository;
import com.triprint.backend.domain.user.entity.User;
import com.triprint.backend.domain.user.service.AwsS3Service;
import com.triprint.backend.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final ImageRepository imageRepository;
    private final AwsS3Service awsS3Service;
    private final UserService userService;
    private final HashtagService hashtagService;
    private final TouristAttractionService touristAttractionService;
    private final TouristAttractionRepository touristAttractionRepository;


    @Transactional
    public Long create(HttpServletRequest request, CreatePostDto createPostDto, List<MultipartFile> images) throws Exception {
        Long userId = (Long) request.getAttribute("userId");
        User author = userService.findById(userId);

        TouristAttraction touristAttraction = touristAttractionService.findOrCreate(createPostDto.getTouristAttraction());
        Post post = Post.builder()
                .author(author)
                .title(createPostDto.getTitle())
                .contents(createPostDto.getContent())
                .touristAttraction(touristAttraction)
                .build();
        images.forEach((img) -> {
            String image = awsS3Service.uploadFile("posts", img);
            post.addImage(imageRepository.save(Image.builder().path(image).build()));
        });
        touristAttraction.addPost(post);
        Post createdPost = postRepository.save(post);
        hashtagService.createPosthashtag(createdPost, createPostDto.getHashtag());

        return createdPost.getId();
    }

    @Transactional
    public ReadPostDto read(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> {
            throw new RuntimeException("해당하는 게시물이 존재하지 않습니다."); });
        ArrayList<String> images = new ArrayList<>();
        ArrayList<String> hashtags = new ArrayList<>();

        for (Image img : post.getImages()){
            images.add(img.getPath());
        }

        for (PostHashtag hashtag : post.getPostHashtag()){
            hashtags.add(hashtag.getHashtag().getContents());
        }

        return ReadPostDto.builder()
                .authorName(post.getAuthor().getUsername())
                .title(post.getTitle())
                .content(post.getContents())
                .hashtags(hashtags)
                .touristAttraction(post.getTouristAttraction())
                .createdAt(post.getCreatedAt())
                .images(images).build();
    }

    @Transactional
    public Long updatePost(Long id, UpdatePostDto updatePostDto, HttpServletRequest request, List<MultipartFile> images) throws Exception {
        Post post = postRepository.findById(id).orElseThrow(() -> {
            throw new RuntimeException("해당 게시물이 없습니다."); });
        Long userId = (Long) request.getAttribute("userId");
        validateIsAuthor(post.getAuthor().getId(),userId);

        List<Image> updateImages = updatePostImages(updatePostDto, post);
        TouristAttraction updateTouristAttraction = updateTouristAttraction(updatePostDto, post);

        images.forEach((img) -> {
            String image = awsS3Service.uploadFile("posts", img);
            Image newImage = imageRepository.save(Image.builder().path(image).build());
            post.addImage(newImage);
            updateImages.add(newImage);
        });

        post.setImages(updateImages); //널값일 경우 에러 뿜게 하면 됨. (스프링 DTO validation으로 검색) -> 위치와 사진만 제한, 이미지 갯수 제한
        post.setTitle(updatePostDto.getTitle());
        post.setContents(updatePostDto.getContents());
        post.setTouristAttraction(updateTouristAttraction);
        post.setPostHashtag(hashtagService.updatePostHashtag(updatePostDto.getHashtag(),post));
        return post.getId();
    }

    @Transactional
    public void deletePost(Long id, HttpServletRequest request){
        Post post = postRepository.findById(id).orElseThrow(() -> {
            throw new RuntimeException("해당 게시물이 없습니다."); });
        Long userId = (Long) request.getAttribute("userId");
        validateIsAuthor(post.getAuthor().getId(), userId);
        postRepository.delete(post);
    }

    private List<Image> updatePostImages(UpdatePostDto updatePostDto, Post post){
        List<String> newImages = updatePostDto.getExistentImages();
        List<Image> existentImages = post.getImages();
        List<Image> updateImages = new ArrayList<>();

        existentImages.forEach((existentImage) -> {
            String existentImg = existentImage.getPath();
            if (!newImages.contains(existentImg)){
                imageRepository.delete(existentImage);
            }else{
                updateImages.add(existentImage);
            }
        });
        return updateImages;
    }

    private TouristAttraction updateTouristAttraction(UpdatePostDto updatePostDto, Post post) throws Exception {

        touristAttractionRepository.delete(post.getTouristAttraction());
        TouristAttraction updateTouristAttraction = touristAttractionService.findOrCreate(updatePostDto.getUpdatedTouristAttraction());
        return updateTouristAttraction;
    }

    private void validateIsAuthor(Long postAuthor, Long currentUserId) {
        if (!currentUserId.equals(postAuthor)) {
            throw new RuntimeException("작성자가 아니므로 수정할 권한이 없습니다.");
        }
    }

}
