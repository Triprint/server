//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.triprint.backend.domain.post.entity;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.triprint.backend.domain.bookmark.entity.Bookmark;
import com.triprint.backend.domain.comment.entity.Reply;
import com.triprint.backend.domain.image.entity.Image;
import com.triprint.backend.domain.like.entity.Like;
import com.triprint.backend.domain.location.entity.TouristAttraction;
import com.triprint.backend.domain.trip.entity.Trip;
import com.triprint.backend.domain.user.entity.User;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

	@Column(length = 50)
	@Size(max = 50)
	private String title;

	@Column(length = 2000)
	@Size(max = 2000)
	private String contents;

	@CreatedDate
	private Timestamp createdAt;

	@LastModifiedDate
	private Timestamp updatedAt;

	@ManyToOne(
		fetch = FetchType.LAZY
	)
	@JoinColumn(
		name = "trip_id"
	)
	private Trip trip;

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
		name = "touristAttraction_id", nullable = false
	)
	private TouristAttraction touristAttraction;

	@OneToMany(
		mappedBy = "post"
	)
	private List<Reply> replies = new ArrayList<>();

	@OneToMany(
		mappedBy = "post", orphanRemoval = true
	)
	private List<PostHashtag> postHashtag = new ArrayList<>();

	@OneToMany(
		mappedBy = "post"
	)
	private List<Bookmark> bookmarks = new ArrayList<>();

	@OneToMany(
		mappedBy = "post", cascade = CascadeType.ALL
	)
	private List<Like> likes = new ArrayList<>();

	@OneToMany(
		mappedBy = "post", cascade = CascadeType.ALL
	)
	@Column(nullable = false, length = 10)
	@Size(min = 1, max = 10)
	private List<Image> images = new ArrayList<>();

	@Builder
	public Post(User author, String title, String contents, TouristAttraction touristAttraction) {
		this.author = author;
		this.title = title;
		this.contents = contents;
		this.touristAttraction = touristAttraction;
	}

	public void addImages(List<Image> postImages) {
		postImages.forEach(this::addImage);
	}

	public void addImage(Image image) {
		if (!this.images.contains(image)) {
			this.images.add(image);
			image.setPost(this);
		}
		if (!image.hasPost()) {
			image.setPost(this);
		}
	}

	public void setTouristAttraction(TouristAttraction touristAttraction) {
		this.touristAttraction = touristAttraction;

		if (!touristAttraction.getPosts().contains(this)) {
			touristAttraction.getPosts().add(this);
		}
	}

	public boolean hasTouristAttraction() {
		return Objects.nonNull(this.touristAttraction);
	}

	public boolean hasTrip() {
		return Objects.nonNull(this.trip);
	}

}
