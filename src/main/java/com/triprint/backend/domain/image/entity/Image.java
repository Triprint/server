//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.triprint.backend.domain.image.entity;

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
public class Image {
	@Id
	@GeneratedValue(
		strategy = GenerationType.IDENTITY
	)
	private Long id;
	private String path;
	private Timestamp createdAt;
	private Timestamp updatedAt;
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

	public String getPath() {
		return this.path;
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

	public Image() {
	}
}
