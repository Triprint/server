//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.triprint.backend.domain.hashtag.entity;

import com.triprint.backend.domain.image.entity.Image;
import com.triprint.backend.domain.post.entity.PostHashtag;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.*;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Hashtag{
	@Id
	@GeneratedValue(
		strategy = GenerationType.IDENTITY
	)
	private Long id;
	private String contents; //db 유니크 해야됨.
	@OneToMany(
		mappedBy = "hashtag", cascade = CascadeType.ALL
	)
	private List<PostHashtag> postHashtag = new ArrayList();

	@Builder
	public Hashtag(String contents){
		this.contents = contents;
	}
}
