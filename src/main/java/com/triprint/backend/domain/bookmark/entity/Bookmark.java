//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.triprint.backend.domain.bookmark.entity;

import com.triprint.backend.domain.member.entity.Member;
import com.triprint.backend.domain.post.entity.Post;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Bookmark {
	@Id
	@GeneratedValue(
		strategy = GenerationType.IDENTITY
	)
	private Long id;
	private int status;
	@ManyToOne(
		fetch = FetchType.LAZY
	)
	@JoinColumn(
		name = "member_id"
	)
	private Member member;
	@ManyToOne(
		fetch = FetchType.LAZY
	)
	@JoinColumn(
		name = "post_id"
	)
	private Post post;

	public Long getId() {
		return this.id;
	}

	public int getStatus() {
		return this.status;
	}

	public Member getMember() {
		return this.member;
	}

	public Post getPost() {
		return this.post;
	}

	public Bookmark() {
	}
}
