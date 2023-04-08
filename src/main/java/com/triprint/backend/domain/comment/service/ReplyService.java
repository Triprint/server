package com.triprint.backend.domain.comment.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.triprint.backend.core.exception.ResourceNotFoundException;
import com.triprint.backend.domain.comment.dto.CreateReplyRequest;
import com.triprint.backend.domain.comment.entity.Reply;
import com.triprint.backend.domain.comment.repository.ReplyRepository;
import com.triprint.backend.domain.post.entity.Post;
import com.triprint.backend.domain.post.repository.PostRepository;
import com.triprint.backend.domain.user.entity.User;
import com.triprint.backend.domain.user.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReplyService {
	private final UserService userService;
	private final PostRepository postRepository;
	private final ReplyRepository replyRepository;

	@Transactional
	public Long create(Long id, CreateReplyRequest createReplyRequest) {
		User author = userService.findById(id);
		User subReplyUser = userService.findById(createReplyRequest.getSubReplyUserId());
		Post post = postRepository.findById(createReplyRequest.getPostId()).orElseThrow(() -> {
			throw new ResourceNotFoundException("해당하는 게시물이 존재하지 않습니다.");
		});
		Reply parentReply = replyRepository.findById(createReplyRequest.getParentReplyId()).orElseThrow(() -> {
			throw new ResourceNotFoundException("해당하는 댓글이 존재하지 않습니다.");
		});
		return 1L;
	}
}
