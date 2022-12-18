//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.triprint.backend.domain.member.entity;

import com.triprint.backend.domain.bookmark.entity.Bookmark;
import com.triprint.backend.domain.comment.entity.Comment;
import com.triprint.backend.domain.like.entity.Like;
import com.triprint.backend.domain.member.status.UserRole;
import com.triprint.backend.domain.post.entity.Post;
import com.triprint.backend.domain.post.entity.PostGroup;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Member {
	@Id
	@GeneratedValue(
		strategy = GenerationType.IDENTITY
	)
	private Long id;
	private String username;
	private String password;
	private String email;
	@Enumerated(EnumType.STRING)
	private UserRole role;
	private String profileImg;
	private Timestamp createdAt;
	private Timestamp updatedAt;
	@OneToMany(
		mappedBy = "member"
	)
	private List<Post> posts = new ArrayList();
	@OneToMany(
		mappedBy = "member"
	)
	private List<Bookmark> bookmarks = new ArrayList();
	@OneToMany(
		mappedBy = "member"
	)
	private List<PostGroup> postGroup = new ArrayList();
	@OneToMany(
		mappedBy = "member"
	)
	private List<Like> likes = new ArrayList();
	@OneToMany(
		mappedBy = "member"
	)
	private List<Comment> comments = new ArrayList();
	@ManyToOne(
		fetch = FetchType.LAZY
	)
	@JoinColumn(
		name = "parent_member_id"
	)
	private Member parent;
	@OneToMany(
		mappedBy = "parent",
		cascade = {CascadeType.ALL}
	)
	private List<Member> children;

}
