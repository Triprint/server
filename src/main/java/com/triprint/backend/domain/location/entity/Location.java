//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.triprint.backend.domain.location.entity;

import com.triprint.backend.domain.post.entity.Post;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Location {
	@Id
	@GeneratedValue(
		strategy = GenerationType.IDENTITY
	)
	private Long id;
	private String name;
	private String location;
	@OneToMany(
		mappedBy = "location"
	)
	private List<Post> posts = new ArrayList();

	public Long getId() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}

	public String getLocation() {
		return this.location;
	}

	public List<Post> getPosts() {
		return this.posts;
	}

	public Location() {
	}
}
