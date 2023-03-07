package com.triprint.backend.domain.post.controller;

import com.triprint.backend.core.valid.ValidFileFormat;
import com.triprint.backend.core.valid.ValidFileSize;
import com.triprint.backend.core.exception.ResourceNotFoundException;
import com.triprint.backend.domain.auth.security.CurrentUser;
import com.triprint.backend.domain.auth.security.UserPrincipal;
import com.triprint.backend.domain.post.dto.CreatePostDto;
import com.triprint.backend.domain.post.dto.UpdatePostDto;
import com.triprint.backend.domain.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
public class PostController {

    private final PostService postService;

    @PostMapping("/posts")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity createPost(@Valid @RequestPart(value = "data") CreatePostDto createPostDto,
                                     @ValidFileFormat(format = {"image/jpeg", "image/png"})
                                     @ValidFileSize(maxSize = 1024)
                                     @RequestPart(value="images") List<MultipartFile> images,
                                     @CurrentUser UserPrincipal userPrincipal) throws Exception {
        return new ResponseEntity<>(postService.create(userPrincipal.getId(), createPostDto, images), HttpStatus.CREATED);
    }

    //Error Handling 확인하기 위한 TEST Code
    @GetMapping("/not")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity not() throws Exception{
        throw new ResourceNotFoundException("아직 정의되지 않은 에러입니다.");
    }

    @GetMapping("/posts/{id}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity readPost(@PathVariable Long id){
        return new ResponseEntity<>(postService.getPost(id), HttpStatus.OK);
    }

    @PutMapping("/posts/{id}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity updatePost(@PathVariable Long id, @RequestPart(value = "data") @Valid UpdatePostDto postUpdateRequestDto,
                                     HttpServletRequest request, @RequestPart(value="images")List<MultipartFile> images) throws Exception {
        return new ResponseEntity<>(postService.updatePost(id, postUpdateRequestDto, request, images), HttpStatus.OK);
    }

    @DeleteMapping("/posts/{id}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<Void> deletePost(@PathVariable Long id, HttpServletRequest request){
        postService.deletePost(id, request);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
