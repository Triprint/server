//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.triprint.backend.domain.post.entity;

import com.triprint.backend.domain.hashtag.entity.Hashtag;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class PostHashtag {
	@Id
	@GeneratedValue(
		strategy = GenerationType.IDENTITY
	)
	private Long id;
	@ManyToOne(
		fetch = FetchType.LAZY
	)
	@JoinColumn(
		name = "post_id"
	)
	private Post post;
	@ManyToOne(
		fetch = FetchType.LAZY
	)
	@JoinColumn(
		name = "hashtag_id"
	)
	private Hashtag hashtag;

}
