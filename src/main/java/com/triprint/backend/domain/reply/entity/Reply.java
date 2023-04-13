//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.triprint.backend.domain.reply.entity;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.triprint.backend.domain.post.entity.Post;
import com.triprint.backend.domain.user.entity.User;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Reply {
	@Id
	@GeneratedValue(
		strategy = GenerationType.IDENTITY
	)
	private Long id;

	private String contents;

	private Timestamp createdAt;

	private Timestamp updatedAt;

	@ManyToOne(
		fetch = FetchType.LAZY
	)
	@JoinColumn(
		name = "post_id", nullable = false
	)
	private Post post;

	@ManyToOne(
		fetch = FetchType.LAZY
	)
	@JoinColumn(
		name = "user_id"
	)
	private User author;

	@ManyToOne(
		fetch = FetchType.LAZY
	)
	@JoinColumn(
		name = "sub_reply_user"
	)
	private User subReplyUser;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "parent_reply_id")
	private Reply parentReply;

	@OneToMany(mappedBy = "parentReply", cascade = CascadeType.ALL)
	private List<Reply> subReply = new ArrayList<>();

	@Builder
	public Reply(User author, String contents, Post post, User subReplyUser, Reply parentReply) {
		this.author = author;
		this.contents = contents;
		this.post = post;
		this.subReplyUser = subReplyUser;
		this.parentReply = parentReply;
		this.setParentReply();
	}

	public void addSubReply(Reply reply) {
		this.subReply.add(reply);
	}

	public void setParentReply() {
		if (this.parentReply != null) {
			this.parentReply.getSubReply().add(this);
		}
	}

	public boolean hasSubReply() {
		return Objects.nonNull(this.subReply);
	}

	public boolean hasParentReply() {
		return Objects.nonNull(this.parentReply);
	}

	public boolean hasSubReplyUser() {
		return Objects.nonNull(this.subReplyUser);
	}

}
