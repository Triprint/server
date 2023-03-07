package com.triprint.backend.domain.post.service;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.triprint.backend.core.exception.BadRequestException;
import com.triprint.backend.core.exception.ForbiddenException;
import com.triprint.backend.core.exception.ResourceNotFoundException;
import com.triprint.backend.domain.hashtag.service.HashtagService;
import com.triprint.backend.domain.image.entity.Image;
import com.triprint.backend.domain.image.repository.ImageRepository;
import com.triprint.backend.domain.image.service.ImageService;
import com.triprint.backend.domain.location.dto.ReadTouristAttractionDto;
import com.triprint.backend.domain.location.entity.TouristAttraction;
import com.triprint.backend.domain.location.service.TouristAttractionService;
import com.triprint.backend.domain.post.dto.CreatePostDto;
import com.triprint.backend.domain.post.dto.ReadPostDto;
import com.triprint.backend.domain.post.dto.UpdatePostDto;
import com.triprint.backend.domain.post.entity.Post;
import com.triprint.backend.domain.post.entity.PostHashtag;
import com.triprint.backend.domain.post.repository.PostRepository;
import com.triprint.backend.domain.user.entity.User;
import com.triprint.backend.domain.user.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostService {

	private final PostRepository postRepository;
	private final ImageRepository imageRepository;
	private final UserService userService;
	private final ImageService imageService;
	private final HashtagService hashtagService;
	private final TouristAttractionService touristAttractionService;

	@Transactional
	public Long create(Long authorId, CreatePostDto createPostDto, List<MultipartFile> images) {
		if (images.size() > 10) {
			throw new BadRequestException("이미지 개수가 올바르지 않습니다. 다시 확인해주세요!");
		}
		User author = userService.findById(authorId);

		TouristAttraction touristAttraction = touristAttractionService.findOrCreate(
			createPostDto.getTouristAttraction());
		Post post = Post.builder()
			.author(author)
			.title(createPostDto.getTitle())
			.contents(createPostDto.getContent())
			.touristAttraction(touristAttraction)
			.build();
		imageService.createImage(images, post);
		touristAttraction.addPost(post);
		Post createdPost = postRepository.save(post);
		assert createPostDto.getHashtag() != null;
		hashtagService.createPosthashtag(createdPost, createPostDto.getHashtag());
		return createdPost.getId();
	}

	@Transactional
	public ReadPostDto getPost(Long postId) {
		Post post = postRepository.findById(postId).orElseThrow(() -> {
			throw new ResourceNotFoundException("해당하는 게시물이 존재하지 않습니다.");
		});
		ArrayList<String> images = new ArrayList<>();
		ArrayList<String> hashtags = new ArrayList<>();

		for (Image img : post.getImages()) {
			images.add(img.getPath());
		}

		for (PostHashtag hashtag : post.getPostHashtag()) {
			hashtags.add(hashtag.getHashtag().getContents());
		}

		ReadTouristAttractionDto readTouristAttractionDto = ReadTouristAttractionDto.builder()
			.x(String.valueOf(post.getTouristAttraction().getLatitudeLongitude().getY()))
			.y(String.valueOf(post.getTouristAttraction().getLatitudeLongitude().getX()))
			.roadNameAddress(post.getTouristAttraction().getRoadNameAddress())
			.name(post.getTouristAttraction().getName())
			.build();

		return ReadPostDto.builder()
			.authorName(post.getAuthor().getUsername())
			.title(post.getTitle())
			.content(post.getContents())
			.hashtags(hashtags)
			.touristAttraction(readTouristAttractionDto)
			.createdAt(post.getCreatedAt())
			.images(images).build();
	}

	@Transactional
	public Long updatePost(Long id, UpdatePostDto updatePostDto, HttpServletRequest request,
		List<MultipartFile> images) {
		if (images.size() + updatePostDto.getExistentImages().size() > 10) {
			throw new BadRequestException("이미지 개수가 올바르지 않습니다. 다시 확인해주세요!");
		}

		Post post = postRepository.findById(id).orElseThrow(() -> {
			throw new ResourceNotFoundException("해당하는 게시물이 존재하지 않습니다.");
		});
		Long userId = (Long)request.getAttribute("userId");
		validateIsAuthor(post.getAuthor().getId(), userId);

		List<Image> updateImages = updatePostImages(updatePostDto, post);
		TouristAttraction updateTouristAttraction = touristAttractionService.updateTouristAttraction(
			updatePostDto.getTouristAttraction());
		updateImages = imageService.updateImage(updateImages, images, post);

		post.setImages(updateImages);
		post.setTitle(updatePostDto.getTitle());
		post.setContents(updatePostDto.getContents());
		post.setTouristAttraction(updateTouristAttraction);
		post.setPostHashtag(hashtagService.updatePostHashtag(updatePostDto.getHashtag(), post));

		return post.getId();
	}

	@Transactional
	public void deletePost(Long id, HttpServletRequest request) {
		Post post = postRepository.findById(id).orElseThrow(() -> {
			throw new ResourceNotFoundException("해당하는 게시물이 존재하지 않습니다.");
		});
		Long userId = (Long)request.getAttribute("userId");
		validateIsAuthor(post.getAuthor().getId(), userId);
		postRepository.delete(post);
	}

	private List<Image> updatePostImages(UpdatePostDto updatePostDto, Post post) {
		List<String> newImages = updatePostDto.getExistentImages();
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
