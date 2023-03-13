//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.triprint.backend.domain.hashtag.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.triprint.backend.domain.post.entity.PostHashtag;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Hashtag {
	@Id
	@GeneratedValue(
		strategy = GenerationType.IDENTITY
	)
	private Long id;

	@Column(columnDefinition = "VARCHAR(45)")
	private String contents;

	@OneToMany(
		mappedBy = "hashtag", cascade = CascadeType.ALL
	)
	private List<PostHashtag> postHashtag = new ArrayList<>();

	@Builder
	public Hashtag(String contents) {
		this.contents = contents;
	}
}
