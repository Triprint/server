//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.triprint.backend.domain.hashtag.entity;

import com.triprint.backend.domain.post.entity.PostHashtag;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Hashtag {
	@Id
	@GeneratedValue(
		strategy = GenerationType.IDENTITY
	)
	private Long id;
	private String contents;
	@OneToMany(
		mappedBy = "hashtag"
	)
	private List<PostHashtag> postHashtag = new ArrayList();

	public Long getId() {
		return this.id;
	}

	public String getContents() {
		return this.contents;
	}

	public List<PostHashtag> getPostHashtag() {
		return this.postHashtag;
	}

	public Hashtag() {
	}
}
