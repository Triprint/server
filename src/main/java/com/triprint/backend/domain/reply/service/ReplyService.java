package com.triprint.backend.domain.reply.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.triprint.backend.core.exception.BadRequestException;
import com.triprint.backend.core.exception.ErrorMessage;
import com.triprint.backend.core.exception.ForbiddenException;
import com.triprint.backend.core.exception.ResourceNotFoundException;
import com.triprint.backend.domain.post.entity.Post;
import com.triprint.backend.domain.post.repository.PostRepository;
import com.triprint.backend.domain.reply.dto.CreateReplyRequest;
import com.triprint.backend.domain.reply.dto.GetReplyResponse;
import com.triprint.backend.domain.reply.dto.ReplyResponse;
import com.triprint.backend.domain.reply.dto.UpdateReplyRequest;
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
	public ReplyResponse create(Long id, CreateReplyRequest createReplyRequest) {
		User author = userRepository.findById(id).orElseThrow(() -> {
			throw new ResourceNotFoundException(ErrorMessage.USER_NOT_FOUND);
		});
		Post post = postRepository.findById(createReplyRequest.getPostId()).orElseThrow(() -> {
			throw new ResourceNotFoundException(ErrorMessage.POST_NOT_FOUND);
		});

		Long subReplyUserId = createReplyRequest.getSubReplyUserId();
		Long parentReplyId = createReplyRequest.getParentReplyId();
		String replyContent = createReplyRequest.getContents();

		//Todo: 둘다 null X => subReplyUser
		if (this.isSubReply(parentReplyId, subReplyUserId)) {
			return this.createSubReply(parentReplyId, subReplyUserId, author, post, replyContent);
		}

		//Todo: 둘다 null => ParentReply
		if (this.isParentReply(parentReplyId, subReplyUserId)) {
			return this.createParentReply(author, post, replyContent);
		}

		//Todo: 둘 중 하나가 null => Exception
		throw new BadRequestException(ErrorMessage.BAD_REQUEST);
	}

	public Page<GetReplyResponse> getReplies(Long postId, Pageable page) {
		Post post = postRepository.findById(postId).orElseThrow(() -> {
			throw new ResourceNotFoundException(ErrorMessage.POST_NOT_FOUND);
		});
		Page<Reply> replies = replyRepository.findByReplyPost(post, page);

		return replies.map(GetReplyResponse::new);
	}

	@Transactional
	public ReplyResponse update(Long id, UpdateReplyRequest updateReplyRequest, Long userId) {
		Reply reply = replyRepository.findById(id).orElseThrow(() -> {
			throw new ResourceNotFoundException(ErrorMessage.REPLY_NOT_FOUND);
		});
		validateIsAuthor(reply.getAuthor().getId(), userId);

		reply.setContents(updateReplyRequest.getContents());
		replyRepository.save(reply);

		return ReplyResponse.builder()
			.replyId(reply.getId())
			.build();
	}

	@Transactional
	public void delete(Long id, Long authorId) {
		Reply reply = replyRepository.findById(id).orElseThrow(() -> {
			throw new ResourceNotFoundException(ErrorMessage.REPLY_NOT_FOUND);
		});
		validateIsAuthor(reply.getAuthor().getId(), authorId);
		replyRepository.delete(reply);
	}

	private void validateIsAuthor(Long replyAuthor, Long currentUserId) {
		if (!currentUserId.equals(replyAuthor)) {
			throw new ForbiddenException(ErrorMessage.INVALID_AUTHOR);
		}
	}

	private boolean isSubReply(Long parentReplyId, Long subReplyUserId) {
		return parentReplyId != null && subReplyUserId != null;
	}

	private boolean isParentReply(Long parentReplyId, Long subReplyUserId) {
		return parentReplyId == null && subReplyUserId == null;
	}

	private ReplyResponse createSubReply(Long parentReplyId, Long subReplyUserId, User author, Post post,
		String contents) {

		Reply parentReply = replyRepository.findById(parentReplyId).orElseThrow(() -> {
			throw new ResourceNotFoundException(ErrorMessage.REPLY_NOT_FOUND);
		});
		User subReplyUser = userRepository.findById(subReplyUserId).orElseThrow(() -> {
			throw new ResourceNotFoundException(ErrorMessage.USER_NOT_FOUND);
		});

		Reply reply = Reply.builder()
			.author(author)
			.contents(contents)
			.post(post)
			.subReplyUser(subReplyUser)
			.parentReply(parentReply)
			.build();

		replyRepository.save(reply);

		return ReplyResponse.builder()
			.replyId(reply.getId())
			.build();
	}

	private ReplyResponse createParentReply(User author, Post post, String contents) {
		Reply reply = Reply.builder()
			.author(author)
			.contents(contents)
			.post(post)
			.subReplyUser(null)
			.parentReply(null)
			.build();

		replyRepository.save(reply);

		return ReplyResponse.builder()
			.replyId(reply.getId())
			.build();
	}
}
