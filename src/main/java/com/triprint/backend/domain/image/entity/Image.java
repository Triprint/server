//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.triprint.backend.domain.image.entity;

import java.sql.Timestamp;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.triprint.backend.domain.post.entity.Post;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Image {
	@Id
	@GeneratedValue(
		strategy = GenerationType.IDENTITY
	)
	private Long id;

	@NotNull
	private String path;

	@CreatedDate
	private Timestamp createdAt;

	@LastModifiedDate
	private Timestamp updatedAt;

	@ManyToOne(
		fetch = FetchType.LAZY
	)
	@JoinColumn(
		name = "post_id"
	)
	private Post post;

	@Builder
	@Size(min = 1, max = 10)
	public Image(String path) {
		this.path = path;
	}

	public void setPost(Post post) {
		this.post = post;

		if (!post.getImages().contains(this)) {
			post.getImages().add(this);
		}
	}

	public boolean hasPost() {
		return Objects.nonNull(this.post);
	}
}
