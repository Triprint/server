//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.triprint.backend.domain.like.entity;

import com.triprint.backend.domain.member.entity.Member;
import com.triprint.backend.domain.post.entity.Post;
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
public class Like {
	@Id
	@GeneratedValue(
		strategy = GenerationType.IDENTITY
	)
	private Long id;
	private int status;
	@ManyToOne(
		fetch = FetchType.LAZY
	)
	@JoinColumn(
		name = "member_id"
	)
	private Member member;
	@ManyToOne(
		fetch = FetchType.LAZY
	)
	@JoinColumn(
		name = "post_id"
	)
	private Post post;

}
