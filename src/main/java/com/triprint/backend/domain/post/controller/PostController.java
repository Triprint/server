package com.triprint.backend.domain.post.controller;

import com.triprint.backend.domain.post.dto.CreatePostDto;
import com.triprint.backend.domain.post.service.PostService;
import com.triprint.backend.domain.user.entity.User;
import com.triprint.backend.domain.user.repository.UserRepository;
import com.triprint.backend.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class PostController {

    private final UserService userService;
    private final PostService postService;

    @PostMapping("/posts")
    public ResponseEntity createPost(@RequestPart(value = "createPostDto") CreatePostDto createPostDto, @RequestPart(value="images") List<MultipartFile> images, HttpServletRequest request) throws IllegalStateException, Exception {
        Long userId = (Long) request.getAttribute("userId");
        User writer = userService.findById(userId);
        return ResponseEntity.ok(postService.create(writer, createPostDto, images));
    }

    @GetMapping("/posts/{id}")
    public ResponseEntity readPost(@PathVariable Long id){
        return ResponseEntity.ok(id);
    }
}
