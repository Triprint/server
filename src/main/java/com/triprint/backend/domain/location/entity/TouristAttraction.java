//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.triprint.backend.domain.location.entity;

import com.triprint.backend.domain.post.entity.Post;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

import com.triprint.backend.domain.post.entity.PostGroup;
import com.triprint.backend.domain.user.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Point;

@Entity
@Getter
@NoArgsConstructor
public class TouristAttraction {
	@Id
	@GeneratedValue(
		strategy = GenerationType.IDENTITY
	)
	private Long id;
	private String touristAttractionName;
	private Point latitudeLongitude;
	private String roadNameAddress; //도로명주소
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
	public TouristAttraction(String touristAttractionName, Point latitudeLongitude, String roadNameAddress, String lotNumberAddress){
		this.touristAttractionName = touristAttractionName;
		this.latitudeLongitude = latitudeLongitude;
		this.roadNameAddress = roadNameAddress;
		this.lotNumberAddress = lotNumberAddress;
	}
}
