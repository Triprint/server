package com.triprint.backend.domain.post.service;

import com.triprint.backend.domain.image.entity.Image;
import com.triprint.backend.domain.post.dto.PostListDto;
import com.triprint.backend.domain.post.entity.Post;
import com.triprint.backend.domain.post.repository.PostRepository;
import com.triprint.backend.domain.user.entity.User;
import com.triprint.backend.domain.user.repository.UserRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {

	private final PostRepository postRepository;
	private final UserRepository userRepository;

	public Page<PostListDto> getPostList(Pageable page) {

		Page<Post> posts = postRepository.findAll(page);

		Page<PostListDto> postListDtos = posts.map(post -> {
				List<String> imagePathes = new ArrayList<>();
				List<Image> images = post.getImages();

				for(Image image : images){
					imagePathes.add(image.getPath());
				}

				return PostListDto.builder()
					.author(post.getUser().getUsername())
					.title(post.getTitle())
					.likes(post.getLikes().size())
					.createdDate(post.getCreatedAt())
					.contents(post.getContents())
					.images(imagePathes)
					.build();
			});

		return postListDtos;
	}

	public Page<PostListDto> getLikePostList(Long userId, Pageable page) {

		User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("일치하는 user 가 없습니다."));
		Page<Post> posts = postRepository.findByLikeUser(user, page);

		Page<PostListDto> postListDtos = posts.map(post -> {
			List<String> imagePathes = new ArrayList<>();
			List<Image> images = post.getImages();

			for(Image image : images){
				imagePathes.add(image.getPath());
			}

			return PostListDto.builder()
				.author(post.getUser().getUsername())
				.title(post.getTitle())
				.likes(post.getLikes().size())
				.createdDate(post.getCreatedAt())
				.contents(post.getContents())
				.images(imagePathes)
				.build();
		});

		return postListDtos;
	}
}
