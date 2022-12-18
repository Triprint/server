//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.triprint.backend.domain.post.entity;

import com.triprint.backend.domain.member.entity.Member;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class PostGroup {
	@Id
	@GeneratedValue(
		strategy = GenerationType.IDENTITY
	)
	private Long id;
	private String title;
	private String location;
	@ManyToOne
	@JoinColumn(
		name = "member_id"
	)
	private Member member;
	@OneToMany(
		mappedBy = "postGroup"
	)
	private List<Post> posts = new ArrayList();

	public Long getId() {
		return this.id;
	}

	public String getTitle() {
		return this.title;
	}

	public String getLocation() {
		return this.location;
	}

	public Member getMember() {
		return this.member;
	}

	public List<Post> getPosts() {
		return this.posts;
	}

	public PostGroup() {
	}
}
