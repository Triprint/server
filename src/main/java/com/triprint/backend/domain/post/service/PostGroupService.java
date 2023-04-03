package com.triprint.backend.domain.post.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.triprint.backend.core.exception.ForbiddenException;
import com.triprint.backend.core.exception.ResourceNotFoundException;
import com.triprint.backend.domain.auth.security.UserPrincipal;
import com.triprint.backend.domain.post.dto.CreateOrUpdatePostGroupRequest;
import com.triprint.backend.domain.post.dto.GetPostGroupResponse;
import com.triprint.backend.domain.post.dto.PostGroupResponse;
import com.triprint.backend.domain.post.entity.Post;
import com.triprint.backend.domain.post.entity.PostGroup;
import com.triprint.backend.domain.post.repository.PostGroupRepository;
import com.triprint.backend.domain.post.repository.PostRepository;
import com.triprint.backend.domain.user.entity.User;
import com.triprint.backend.domain.user.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostGroupService {

	private final UserService userService;
	private final PostRepository postRepository;
	private final PostGroupRepository postGroupRepository;

	@Transactional
	public PostGroupResponse create(Long id, CreateOrUpdatePostGroupRequest createPostGroupRequest) {
		User author = userService.findById(id);

		PostGroup postGroup = PostGroup.builder()
			.author(author)
			.title(createPostGroupRequest.getTitle())
			.build();

		createPostGroupRequest.getPosts().forEach((postId) -> {
			Post post = postRepository.findById(postId).orElseThrow(() -> {
				throw new ResourceNotFoundException("해당하는 게시물이 존재하지 않습니다.");
			});
			validateIsAuthor(post.getAuthor().getId(), author.getId());
			postGroup.addPost(post);
		});
		postGroupRepository.save(postGroup);
		return new PostGroupResponse(postGroup.getId());
	}

	@Transactional
	public GetPostGroupResponse getPostGroup(Long id, UserPrincipal userPrincipal) {
		PostGroup postGroup = postGroupRepository.findById(id).orElseThrow(() -> {
			throw new ResourceNotFoundException("해당하는 게시물 그룹이 존재하지 않습니다.");
		});

		return new GetPostGroupResponse(postGroup);
	}

	@Transactional
	public PostGroupResponse update(Long id, Long authorId, CreateOrUpdatePostGroupRequest updatePostGroupRequest) {
		PostGroup postGroup = postGroupRepository.findById(id).orElseThrow(() -> {
			throw new ResourceNotFoundException("해당하는 게시물 그룹이 존재하지 않습니다.");
		});
		validateIsAuthor(postGroup.getUser().getId(), authorId);

		postGroup.removePosts();
		updatePostGroupRequest.getPosts().forEach((postId) -> {
			Post post = postRepository.findById(postId).orElseThrow(() -> {
				throw new ResourceNotFoundException("해당하는 게시물이 존재하지 않습니다.");
			});
			validateIsAuthor(post.getAuthor().getId(), authorId);
			postGroup.addPost(post);
		});
		postGroup.setTitle(updatePostGroupRequest.getTitle());
		postGroupRepository.save(postGroup);
		return new PostGroupResponse(postGroup.getId());
	}

	@Transactional
	public void delete(Long id, Long authorId) {
		PostGroup postGroup = postGroupRepository.findById(id).orElseThrow(() -> {
			throw new ResourceNotFoundException("해당하는 게시물이 존재하지 않습니다.");
		});
		validateIsAuthor(postGroup.getUser().getId(), authorId);
		postGroupRepository.delete(postGroup);
	}

	private void validateIsAuthor(Long postAuthor, Long currentUserId) {
		if (!currentUserId.equals(postAuthor)) {
			throw new ForbiddenException("작성자가 아니므로 수정할 권한이 없습니다.");
		}
	}
}
