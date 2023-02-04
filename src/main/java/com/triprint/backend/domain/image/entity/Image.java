//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.triprint.backend.domain.image.entity;

import com.sun.istack.NotNull;
import com.triprint.backend.domain.post.entity.Post;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;
import javax.persistence.*;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

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
	public Image(String path){
		this.path = path;
	}

	public void setPost(Post post){
		this.post = post;

		if(!post.getImages().contains(this))
			post.getImages().add(this);
	}

	public boolean hasPost() {
		return Objects.nonNull(this.post);
	}

	public boolean isContainsOf(List<Long> imageIds) {
		return imageIds.contains(this.getId());
	}
}
