//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.triprint.backend.domain.location.entity;

import com.triprint.backend.domain.image.entity.Image;
import com.triprint.backend.domain.post.entity.Post;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Point;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TouristAttraction {
	@Id
	@GeneratedValue(
		strategy = GenerationType.IDENTITY
	)
	private Long id;
	private String name; //변수명 변환하기
	private Point latitudeLongitude;

	@Column(nullable = false)
	private String roadNameAddress; //도로명주소 -> district

	@ManyToOne(
			fetch = FetchType.LAZY
	)
	@JoinColumn(
			name = "district_id"
	)
	private District district;

	@OneToMany(
		mappedBy = "touristAttraction", cascade = CascadeType.ALL
	)
	private List<Post> posts = new ArrayList();

	@Builder
	public TouristAttraction(String name, Point latitudeLongitude, String roadNameAddress){
		this.name = name;
		this.latitudeLongitude = latitudeLongitude;
		this.roadNameAddress = roadNameAddress;
	}

	public void addPost(Post post) {
		if (!this.posts.contains(post)){
			this.posts.add(post);
			post.setTouristAttraction(this);
		}
		if (!post.hasTouristAttraction()){
			post.setTouristAttraction(this);
		}
	}

	public void setDistrict(District district){
		this.district = district;

		if(!district.getTouristAttractions().contains(this))
			district.getTouristAttractions().add(this);
	}

	public boolean hasDistrict() {
		return Objects.nonNull(this.district);
	}
}
