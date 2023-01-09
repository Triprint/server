package com.triprint.backend.domain.post.service;

import com.triprint.backend.domain.image.entity.Image;
import com.triprint.backend.domain.like.entity.Like;
import com.triprint.backend.domain.post.dto.PostListDto;
import com.triprint.backend.domain.post.entity.Post;
import com.triprint.backend.domain.post.repository.PostRepository;
import com.triprint.backend.domain.user.entity.User;
import com.triprint.backend.domain.user.repository.UserRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {

	private final PostRepository postRepository;
	private final UserRepository userRepository;

	public List<PostListDto> getPostList() {

		List<Post> posts = postRepository.findAll();
		List<PostListDto> postListDtos = new ArrayList<>();

		for(Post post : posts) {
			List<String> imagePathes = new ArrayList<>();
			List<Image> images = post.getImages();

			for(Image image : images){
				imagePathes.add(image.getPath());
			}

			PostListDto postListDto = PostListDto.builder()
				.author(post.getUser().getUsername())
				.likes(post.getLikes().size())
				.createdDate(post.getCreatedAt())
				.contents(post.getContents())
				.images(imagePathes)
				.build();

			postListDtos.add(postListDto);
		}

		return postListDtos;
	}

	public List<PostListDto> getLikePostList(Long userId) {

		User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("일치하는 user 가 없습니다."));

		List<Post> posts = new ArrayList<>();
		List<Like> likes = user.getLikes();

		for(Like like : likes) {
			posts.add(like.getPost());
		}

		List<PostListDto> postListDtos = new ArrayList<>();

		for(Post post : posts) {
			List<String> imagePathes = new ArrayList<>();
			List<Image> images = post.getImages();

			for(Image image : images){
				imagePathes.add(image.getPath());
			}

			PostListDto postListDto = PostListDto.builder()
				.author(post.getUser().getUsername())
				.likes(post.getLikes().size())
				.createdDate(post.getCreatedAt())
				.contents(post.getContents())
				.images(imagePathes)
				.build();

			postListDtos.add(postListDto);
		}

		return postListDtos;
	}


}
