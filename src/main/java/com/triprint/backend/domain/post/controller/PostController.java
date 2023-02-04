package com.triprint.backend.domain.post.controller;

import com.sun.istack.NotNull;
import com.triprint.backend.domain.post.dto.CreatePostDto;
import com.triprint.backend.domain.post.dto.UpdatePostDto;
import com.triprint.backend.domain.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping("/posts")
    public ResponseEntity createPost(@RequestPart(value = "data") CreatePostDto createPostDto, @NotNull @RequestPart(value="images") List<MultipartFile> images,
                                     HttpServletRequest request) throws Exception {
        return ResponseEntity.ok(postService.create(request, createPostDto, images));
    }

    @GetMapping("/posts/{id}")
    public ResponseEntity readPost(@PathVariable Long id){
        return ResponseEntity.ok(postService.getPost(id));
    }

    @PutMapping("/posts/{id}")
    public ResponseEntity updatePost(@PathVariable Long id, @RequestPart(value = "data") UpdatePostDto postUpdateRequestDto,
                                     HttpServletRequest request, @RequestPart(value="images")List<MultipartFile> images) throws Exception {
        return ResponseEntity.ok(postService.updatePost(id, postUpdateRequestDto, request, images));
    }

    @DeleteMapping("/posts/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id, HttpServletRequest request){
        postService.deletePost(id, request);
        return ResponseEntity.noContent().build();
    }
}
