package com.triprint.backend.domain.reply.dto;

import com.triprint.backend.domain.reply.entity.Reply;
import com.triprint.backend.domain.user.entity.User;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReplyDto {
	private Reply parentReply;
	private User subReplyUser;
}
