//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.triprint.backend.domain.post.entity;

import com.triprint.backend.domain.bookmark.entity.Bookmark;
import com.triprint.backend.domain.comment.entity.Comment;
import com.triprint.backend.domain.image.entity.Image;
import com.triprint.backend.domain.like.entity.Like;
import com.triprint.backend.domain.location.entity.TouristAttraction;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

import com.triprint.backend.domain.user.entity.User;
import lombok.*;
import org.locationtech.jts.geom.Point;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

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
	@Nullable
	private String title;
	@Nullable
	private String contents;
	private Point latitudeLongitude;

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
		name = "touristAttraction_id"
	)
	private TouristAttraction touristAttraction;
	@OneToMany(
		mappedBy = "post"
	)
	private List<Comment> comments = new ArrayList();
	@OneToMany(
		mappedBy = "post", orphanRemoval = true
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
			mappedBy = "post", cascade = CascadeType.ALL
	)
	@NonNull
	private List<Image> images = new ArrayList();

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
