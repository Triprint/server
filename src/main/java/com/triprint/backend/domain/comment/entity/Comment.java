//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.triprint.backend.domain.comment.entity;

import com.triprint.backend.domain.member.entity.Member;
import com.triprint.backend.domain.post.entity.Post;
import java.sql.Timestamp;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Comment {
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
		name = "member_id"
	)
	private Member member;

	public Long getId() {
		return this.id;
	}

	public String getContents() {
		return this.contents;
	}

	public Timestamp getCreatedAt() {
		return this.createdAt;
	}

	public Timestamp getUpdatedAt() {
		return this.updatedAt;
	}

	public Post getPost() {
		return this.post;
	}

	public Member getMember() {
		return this.member;
	}

	public Comment() {
	}
}
