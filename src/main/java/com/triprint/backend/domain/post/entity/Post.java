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
import java.util.stream.Collectors;
import javax.persistence.*;

import com.triprint.backend.domain.user.entity.User;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Getter
@Setter
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
	private List<PostHashtag> postHashtag = new ArrayList(); //1. 태그가 있으면 가져오고 없으면 생성 2.update경우 어떻게 할지 생각하기(이미지를 수정했다면 그거에 대한 요청을 어떻게 받을 건지)
	@OneToMany(
		mappedBy = "post"
	)
	private List<Bookmark> bookmarks = new ArrayList();
	@OneToMany(
		mappedBy = "post"
	)
	private List<Like> likes = new ArrayList();

	@OneToMany(
			mappedBy = "post"
	)
	private List<Image> images = new ArrayList<>();

	@Builder
	public Post(User author, String title, String contents){
		this.author = author;
		this.title = title;
		this.contents = contents;
	}

	public void addImages(List<Image> postImages) {
		postImages.forEach(this::addImage);
	}

	public void addImage(Image image) {
		if (!this.images.contains(image)){
			this.images.add(image);
			image.setPost(this);
		}
		if (!image.hasPost()){
			image.setPost(this);
		}
	}
}
