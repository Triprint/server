//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.triprint.backend.domain.post.entity;

import com.triprint.backend.domain.bookmark.entity.Bookmark;
import com.triprint.backend.domain.comment.entity.Comment;
import com.triprint.backend.domain.image.entity.Image;
import com.triprint.backend.domain.like.entity.Like;
import com.triprint.backend.domain.location.entity.Location;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

import com.triprint.backend.domain.user.entity.User;
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
public class Post {
	@Id
	@GeneratedValue(
		strategy = GenerationType.IDENTITY
	)
	private Long id;
	private String title;
	private String contents;
	private Double latitude;
	private Double longitude;
	@CreatedDate
	private Timestamp createdAt;
	@LastModifiedDate
	private Timestamp updatedAt;
	@ManyToOne(
		fetch = FetchType.LAZY
	)
	@JoinColumn(
		name = "group_id"
	)
	private PostGroup postGroup;

	@ManyToOne(
		fetch = FetchType.LAZY
	)
	@JoinColumn(
		name = "author_id"
	)
	private User author;

	@ManyToOne(
		fetch = FetchType.LAZY
	)
	@JoinColumn(
		name = "location_id"
	)
	private Location location;
	@OneToMany(
		mappedBy = "post"
	)
	private List<Comment> comments = new ArrayList();
	@OneToMany(
		mappedBy = "post"
	)
	private List<PostHashtag> postHashtag = new ArrayList();
	@OneToMany(
		mappedBy = "post"
	)
	private List<Bookmark> bookmarks = new ArrayList();
	@OneToMany(
		mappedBy = "post"
	)
	private List<Like> likes = new ArrayList();

	@OneToMany(
			mappedBy = "post",
			cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
			orphanRemoval = true
	)
	private List<Image> images = new ArrayList<>();

	@Builder
	public Post(User author, String title, String contents){
		this.author = author;
		this.title = title;
		this.contents = contents;
	}

	public void addImage(Image image) {
		this.images.add(image);
		image.setPost(this);
	}
}
