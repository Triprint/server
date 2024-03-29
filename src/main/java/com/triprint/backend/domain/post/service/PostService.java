package com.triprint.backend.domain.post.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.triprint.backend.core.exception.BadRequestException;
import com.triprint.backend.core.exception.ErrorMessage;
import com.triprint.backend.core.exception.ForbiddenException;
import com.triprint.backend.core.exception.ResourceNotFoundException;
import com.triprint.backend.domain.auth.security.UserPrincipal;
import com.triprint.backend.domain.hashtag.service.HashtagService;
import com.triprint.backend.domain.image.entity.Image;
import com.triprint.backend.domain.image.repository.ImageRepository;
import com.triprint.backend.domain.image.service.ImageService;
import com.triprint.backend.domain.like.service.LikeService;
import com.triprint.backend.domain.location.entity.TouristAttraction;
import com.triprint.backend.domain.location.service.TouristAttractionService;
import com.triprint.backend.domain.post.dto.CreatePostRequest;
import com.triprint.backend.domain.post.dto.GetPostResponse;
import com.triprint.backend.domain.post.dto.PostResponse;
import com.triprint.backend.domain.post.dto.UpdatePostRequest;
import com.triprint.backend.domain.post.entity.Post;
import com.triprint.backend.domain.post.repository.PostRepository;
import com.triprint.backend.domain.user.entity.User;
import com.triprint.backend.domain.user.repository.UserRepository;
import com.triprint.backend.domain.user.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostService {

	private final PostRepository postRepository;
	private final UserRepository userRepository;
	private final ImageRepository imageRepository;
	private final UserService userService;
	private final ImageService imageService;
	private final HashtagService hashtagService;
	private final TouristAttractionService touristAttractionService;
	private final LikeService likeService;

	public Page<GetPostResponse> getPostListWithUnAuthenticated(Page<Post> posts) {
		return posts.map((post) -> new GetPostResponse(post, false));
	}

	public Page<GetPostResponse> getPostListWithAuthenticated(Page<Post> posts, User user) {
		return posts.map((post) -> {
			boolean like = likeService.isLike(user, post);
			return new GetPostResponse(post, like);
		});
	}

	public Page<GetPostResponse> getPostList(Pageable page, UserPrincipal userPrincipal) {
		Page<Post> posts = postRepository.findAll(page);
		if (userPrincipal == null) {
			return this.getPostListWithUnAuthenticated(posts);
		}
		Optional<User> user = userRepository.findById(userPrincipal.getId());
		if (user.isEmpty()) {
			return this.getPostListWithUnAuthenticated(posts);
		}
		return this.getPostListWithAuthenticated(posts, user.get());
	}

	public Page<GetPostResponse> getLikePostList(Long userId, Pageable page) {
		User user = userRepository.findById(userId)
			.orElseThrow(() -> new ResourceNotFoundException(ErrorMessage.USER_NOT_FOUND));
		Page<Post> posts = postRepository.findByLikeUser(user, page);
		return posts.map((post) -> new GetPostResponse(post, true));
	}

	public GetPostResponse getPost(Long postId, UserPrincipal userPrincipal) {
		Post post = postRepository.findById(postId).orElseThrow(() -> {
			throw new ResourceNotFoundException(ErrorMessage.POST_NOT_FOUND);
		});
		if (userPrincipal == null) {
			return new GetPostResponse(post, false);
		}
		Optional<User> user = userRepository.findById(userPrincipal.getId());
		if (user.isEmpty()) {
			return new GetPostResponse(post, false);
		}
		boolean like = likeService.isLike(user.get(), post);
		return new GetPostResponse(post, like);
	}

	@Transactional
	public PostResponse create(Long authorId, CreatePostRequest createPostRequest, List<MultipartFile> images) {
		if (images.size() > 10) {
			throw new BadRequestException("이미지 개수가 올바르지 않습니다. 다시 확인해주세요!");
		}
		User author = userService.findById(authorId);

		TouristAttraction touristAttraction = touristAttractionService.findOrCreate(
			createPostRequest.getTouristAttraction());
		Post post = Post.builder()
			.author(author)
			.title(createPostRequest.getTitle())
			.contents(createPostRequest.getContent())
			.touristAttraction(touristAttraction)
			.build();
		imageService.createImage(images, post);
		touristAttraction.addPost(post);
		Post createdPost = postRepository.save(post);
		assert createPostRequest.getHashtag() != null;
		hashtagService.createPosthashtag(createdPost, createPostRequest.getHashtag());
		return PostResponse.builder()
			.id(createdPost.getId())
			.build();
	}

	@Transactional
	public PostResponse updatePost(Long id, UpdatePostRequest updatePostRequest, Long userId,
		List<MultipartFile> images) {
		if (images.size() + updatePostRequest.getExistentImages().size() > 10) {
			throw new BadRequestException("이미지 개수가 올바르지 않습니다. 다시 확인해주세요!");
		}

		Post post = postRepository.findById(id).orElseThrow(() -> {
			throw new ResourceNotFoundException(ErrorMessage.POST_NOT_FOUND);
		});
		validateIsAuthor(post.getAuthor().getId(), userId);

		List<Image> updateImages = updatePostImages(updatePostRequest, post);
		TouristAttraction updateTouristAttraction = touristAttractionService.updateTouristAttraction(
			updatePostRequest.getTouristAttraction());
		updateImages = imageService.updateImage(updateImages, images, post);

		post.setImages(updateImages);
		post.setTitle(updatePostRequest.getTitle());
		post.setContents(updatePostRequest.getContents());
		post.setTouristAttraction(updateTouristAttraction);
		post.setPostHashtag(hashtagService.updatePostHashtag(updatePostRequest.getHashtag(), post));

		return PostResponse.builder()
			.id(post.getId())
			.build();
	}

	@Transactional
	public void deletePost(Long id, Long userId) {
		Post post = postRepository.findById(id).orElseThrow(() -> {
			throw new ResourceNotFoundException(ErrorMessage.POST_NOT_FOUND);
		});
		validateIsAuthor(post.getAuthor().getId(), userId);
		postRepository.delete(post);
	}

	private List<Image> updatePostImages(UpdatePostRequest updatePostRequest, Post post) {
		List<String> newImages = updatePostRequest.getExistentImages();
		List<Image> existentImages = post.getImages();
		List<Image> updateImages = new ArrayList<>();

		existentImages.forEach((existentImage) -> {
			String existentImg = existentImage.getPath();
			if (!newImages.contains(existentImg)) {
				imageRepository.delete(existentImage);
			} else {
				updateImages.add(existentImage);
			}
		});
		return updateImages;
	}

	private void validateIsAuthor(Long postAuthor, Long currentUserId) {
		if (!currentUserId.equals(postAuthor)) {
			throw new ForbiddenException("작성자가 아니므로 수정할 권한이 없습니다.");
		}
	}
}
