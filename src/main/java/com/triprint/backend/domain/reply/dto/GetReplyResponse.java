package com.triprint.backend.domain.reply.dto;

import java.sql.Timestamp;

import com.triprint.backend.domain.reply.entity.Reply;
import com.triprint.backend.domain.user.dto.AuthorInfoResponse;

import lombok.Getter;

@Getter
public class GetReplyResponse {
	private final Long id;
	private final AuthorInfoResponse author;
	private final String contents;
	private final Long parentReplyId;
	private final String subReplyUserName;
	private final Boolean isReply;
	private final Integer replyCnt;
	private final Timestamp createdAt;
	private final Timestamp updatedAt;

	public GetReplyResponse(Reply reply) {
		this.author = new AuthorInfoResponse(reply.getAuthor());
		this.id = reply.getId();
		this.contents = reply.getContents();
		this.parentReplyId = reply.hasParentReply() ? reply.getParentReply().getId() : null;
		this.subReplyUserName = reply.hasSubReplyUser() ? reply.getSubReplyUser().getUsername() :
			reply.hasParentReply() ? reply.getParentReply().getAuthor().getUsername() : null;
		this.isReply = reply.hasSubReply();
		this.replyCnt = reply.hasSubReply() ? reply.getSubReply().size() : 0;
		this.createdAt = reply.getCreatedAt();
		this.updatedAt = reply.getUpdatedAt();
	}
}
