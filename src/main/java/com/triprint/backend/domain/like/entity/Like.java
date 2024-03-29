//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.triprint.backend.domain.like.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.triprint.backend.domain.post.entity.Post;
import com.triprint.backend.domain.user.entity.User;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(uniqueConstraints = {
	@UniqueConstraint(name = "likes_uk", columnNames = {"post_id", "user_id"})}, name = "likes")
public class Like {
	@Id
	@GeneratedValue(
		strategy = GenerationType.IDENTITY
	)
	private Long id;

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
		name = "post_id"
	)
	private Post post;

	@Builder
	Like(Post post, User user) {
		setLikeAndUser(user);
		setLikeAndPost(post);
	}

	public void setLikeAndPost(Post post) {
		this.post = post;
		post.getLikes().add(this);
	}

	public void setLikeAndUser(User user) {
		this.user = user;
		user.getLikes().add(this);
	}
}
