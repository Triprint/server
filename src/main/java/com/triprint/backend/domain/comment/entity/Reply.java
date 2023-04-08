//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.triprint.backend.domain.comment.entity;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

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

import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
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
		name = "post_id"
	)
	private Post post;
	@ManyToOne(
		fetch = FetchType.LAZY
	)
	@JoinColumn(
		name = "user_id"
	)
	private User user;

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
}
