//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.triprint.backend.domain.post.entity;

import com.triprint.backend.domain.bookmark.entity.Bookmark;
import com.triprint.backend.domain.comment.entity.Comment;
import com.triprint.backend.domain.like.entity.Like;
import com.triprint.backend.domain.location.entity.Location;
import com.triprint.backend.domain.member.entity.Member;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
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
	private Timestamp createdAt;
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
	private Member member;
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

	public Long getId() {
		return this.id;
	}

	public String getTitle() {
		return this.title;
	}

	public String getContents() {
		return this.contents;
	}

	public Double getLatitude() {
		return this.latitude;
	}

	public Double getLongitude() {
		return this.longitude;
	}

	public Timestamp getCreatedAt() {
		return this.createdAt;
	}

	public Timestamp getUpdatedAt() {
		return this.updatedAt;
	}

	public PostGroup getPostGroup() {
		return this.postGroup;
	}

	public Member getMember() {
		return this.member;
	}

	public Location getLocation() {
		return this.location;
	}

	public List<Comment> getComments() {
		return this.comments;
	}

	public List<PostHashtag> getPostHashtag() {
		return this.postHashtag;
	}

	public List<Bookmark> getBookmarks() {
		return this.bookmarks;
	}

	public List<Like> getLikes() {
		return this.likes;
	}

	public Post() {
	}
}
