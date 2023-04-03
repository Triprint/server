//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.triprint.backend.domain.post.entity;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import com.triprint.backend.domain.user.entity.User;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@NoArgsConstructor
public class PostGroup {
	@Id
	@GeneratedValue(
		strategy = GenerationType.IDENTITY
	)
	private Long id;

	@Setter
	private String title;

	@ManyToOne
	@JoinColumn(
		name = "user_id"
	)
	private User user;

	@OneToMany(
		mappedBy = "postGroup"
	)
	private List<Post> posts = new ArrayList<>();

	@CreatedDate
	private Timestamp createdAt;

	@LastModifiedDate
	private Timestamp updatedAt;

	@Builder
	public PostGroup(User author, String title) {
		this.user = author;
		this.title = title;
	}

	public void removePosts() {
		this.posts.forEach((post) -> {
			post.setPostGroup(null);
		});

		this.posts = new ArrayList<>();
	}

	public void addPost(Post post) {
		this.posts.add(post);
		post.setPostGroup(this);
	}
}
