package com.triprint.backend.domain.follow.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.triprint.backend.domain.user.entity.User;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Follow {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "follower_id")
	private User follower; //팔로우를 받는 사람

	@ManyToOne
	@JoinColumn(name = "following_id")
	private User following; //팔로우를 하는 사람

	@Builder
	public Follow(User follower, User following) {
		this.follower = follower;
		this.following = following;
		following.getFollowers().add(this);
		follower.getFollowings().add(this);
	}
}
