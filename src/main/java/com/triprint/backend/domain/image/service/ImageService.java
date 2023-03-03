package com.triprint.backend.domain.image.service;

import com.triprint.backend.domain.image.entity.Image;
import com.triprint.backend.domain.image.repository.ImageRepository;
import com.triprint.backend.domain.post.entity.Post;
import com.triprint.backend.domain.user.service.AwsS3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final AwsS3Service awsS3Service;
    private final ImageRepository imageRepository;

    public void createImage(@Validated List<MultipartFile> images, Post post){
        images.forEach((img) -> {
            String image = awsS3Service.uploadFile("posts", img);
            post.addImage(imageRepository.save(Image.builder().path(image).build()));
        });
    }

    public List<Image> updateImage(List<Image> updateImages, List<MultipartFile> images, Post post){
        images.forEach((img) -> {
            String image = awsS3Service.uploadFile("posts", img);
            Image newImage = imageRepository.save(Image.builder().path(image).build());
            post.addImage(newImage);
            updateImages.add(newImage);
        });
        return updateImages;
    }
}
