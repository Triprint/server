package com.triprint.backend.domain.reply.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.triprint.backend.core.exception.ForbiddenException;
import com.triprint.backend.core.exception.ResourceNotFoundException;
import com.triprint.backend.domain.post.entity.Post;
import com.triprint.backend.domain.post.repository.PostRepository;
import com.triprint.backend.domain.reply.dto.GetReplyResponse;
import com.triprint.backend.domain.reply.dto.ReplyDto;
import com.triprint.backend.domain.reply.dto.ReplyRequest;
import com.triprint.backend.domain.reply.dto.ReplyResponse;
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
	public ReplyResponse create(Long id, ReplyRequest createReplyRequest) {
		User author = userRepository.findById(id).orElseThrow(() -> {
			throw new ResourceNotFoundException("일치하는 User가 존재하지 않습니다.");
		});
		ReplyDto replyDto = this.isExist(createReplyRequest);

		Reply reply = Reply.builder()
			.author(author)
			.contents(createReplyRequest.getContents())
			.post(replyDto.getPost())
			.subReplyUser(replyDto.getSubReplyUser())
			.parentReply(replyDto.getParentReply())
			.build();

		replyRepository.save(reply);

		return ReplyResponse.builder()
			.replyId(reply.getId())
			.build();
	}

	public Page<GetReplyResponse> getReplies(Long postId, Pageable page) {
		Post post = postRepository.findById(postId).orElseThrow(() -> {
			throw new ResourceNotFoundException("해당하는 게시물이 존재하지 않습니다.");
		});
		Page<Reply> replies = replyRepository.findByReplyPost(post, page);

		return replies.map((reply) -> new GetReplyResponse(reply));
	}

	@Transactional
	public ReplyResponse update(Long id, ReplyRequest updateReplyRequest, Long userId) {
		Reply reply = replyRepository.findById(id).orElseThrow(() -> {
			throw new ResourceNotFoundException("해당하는 댓글이 존재하지 않습니다.");
		});
		validateIsAuthor(reply.getAuthor().getId(), userId);

		ReplyDto replyDto = this.isExist(updateReplyRequest);

		reply.setPost(replyDto.getPost());
		reply.setContents(updateReplyRequest.getContents());
		reply.setParentReply(replyDto.getParentReply());
		reply.setSubReplyUser(replyDto.getSubReplyUser());
		replyRepository.save(reply);

		return ReplyResponse.builder()
			.replyId(reply.getId())
			.build();
	}

	@Transactional
	public void delete(Long id, Long authorId) {
		Reply reply = replyRepository.findById(id).orElseThrow(() -> {
			throw new ResourceNotFoundException("해당하는 게시물이 존재하지 않습니다.");
		});
		validateIsAuthor(reply.getAuthor().getId(), authorId);
		replyRepository.delete(reply);
	}

	private void validateIsAuthor(Long replyAuthor, Long currentUserId) {
		if (!currentUserId.equals(replyAuthor)) {
			throw new ForbiddenException("작성자가 아니므로 수정할 권한이 없습니다.");
		}
	}

	private ReplyDto isExist(ReplyRequest replyRequest) {
		User subReplyUser = null;
		Reply parentReply = null;
		Post post = postRepository.findById(replyRequest.getPostId()).orElseThrow(() -> {
			throw new ResourceNotFoundException("해당하는 게시물이 존재하지 않습니다.");
		});

		if (replyRequest.getParentReplyId() != null) {
			parentReply = replyRepository.findById(replyRequest.getParentReplyId()).orElseThrow(() -> {
				throw new ResourceNotFoundException("해당하는 댓글이 존재하지 않습니다.");
			});
		}
		if (replyRequest.getSubReplyUserId() != null) {
			subReplyUser = userRepository.findById(replyRequest.getSubReplyUserId()).orElseThrow(() -> {
				throw new ResourceNotFoundException("일치하는 User가 존재하지 않습니다.");
			});
		}
		return ReplyDto.builder()
			.post(post)
			.subReplyUser(subReplyUser)
			.parentReply(parentReply)
			.build();
	}
}
