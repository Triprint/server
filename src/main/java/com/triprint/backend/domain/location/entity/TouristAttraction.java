//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.triprint.backend.domain.location.entity;

import com.triprint.backend.domain.image.entity.Image;
import com.triprint.backend.domain.post.entity.Post;
import java.util.ArrayList;
import java.util.List;
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
	private String touristAttraction;
	private Point latitudeLongitude;

	@Column(nullable = false)
	private String roadNameAddress; //도로명주소 -> district
	private String lotNumberAddress; //지번주소

	@ManyToOne(
			fetch = FetchType.LAZY
	)
	@JoinColumn(
			name = "district_id"
	)
	private District district;

	@OneToMany(
		mappedBy = "touristAttraction"
	)
	private List<Post> posts = new ArrayList();

	@Builder
	public TouristAttraction(String touristAttraction, Point latitudeLongitude, String roadNameAddress, String lotNumberAddress){
		this.touristAttraction = touristAttraction;
		this.latitudeLongitude = latitudeLongitude;
		this.roadNameAddress = roadNameAddress;
		this.lotNumberAddress = lotNumberAddress;
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
}
