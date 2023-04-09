package com.triprint.backend.domain.reply.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.triprint.backend.core.exception.ResourceNotFoundException;
import com.triprint.backend.domain.post.entity.Post;
import com.triprint.backend.domain.post.repository.PostRepository;
import com.triprint.backend.domain.reply.dto.CreateReplyRequest;
import com.triprint.backend.domain.reply.dto.GetReplyResponse;
import com.triprint.backend.domain.reply.entity.Reply;
import com.triprint.backend.domain.reply.repository.ReplyRepository;
import com.triprint.backend.domain.user.entity.User;
import com.triprint.backend.domain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReplyService {
	private final UserRepository userRepository;
	private final PostRepository postRepository;
	private final ReplyRepository replyRepository;

	@Transactional
	public Long create(Long id, CreateReplyRequest createReplyRequest) {
		User subReplyUser = null;
		Reply parentReply = null;
		User author = userRepository.findById(id).orElseThrow(() -> {
			throw new ResourceNotFoundException("일치하는 User가 존재하지 않습니다.");
		});
		Post post = postRepository.findById(createReplyRequest.getPostId()).orElseThrow(() -> {
			throw new ResourceNotFoundException("해당하는 게시물이 존재하지 않습니다.");
		});

		if (createReplyRequest.getParentReplyId() != null) {
			parentReply = replyRepository.findById(createReplyRequest.getParentReplyId()).orElseThrow(() -> {
				throw new ResourceNotFoundException("해당하는 댓글이 존재하지 않습니다.");
			});
		}
		if (createReplyRequest.getSubReplyUserId() != null) {
			subReplyUser = userRepository.findById(createReplyRequest.getSubReplyUserId()).orElseThrow(() -> {
				throw new ResourceNotFoundException("일치하는 User가 존재하지 않습니다.");
			});
		}

		Reply reply = Reply.builder()
			.author(author)
			.contents(createReplyRequest.getContents())
			.post(post)
			.subReplyUser(subReplyUser)
			.parentReply(parentReply)
			.build();

		replyRepository.save(reply);

		return reply.getId();
	}

	public Page<GetReplyResponse> getReplies(Long postId, Pageable page) {
		Post post = postRepository.findById(postId).orElseThrow(() -> {
			throw new ResourceNotFoundException("해당하는 게시물이 존재하지 않습니다.");
		});
		Page<Reply> replies = replyRepository.findByReplyPost(post, page);

		return replies.map((reply) -> new GetReplyResponse(reply));
	}
}
