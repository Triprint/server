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
	private Point location;
	private String roadNameAddress;
	private String lotNumberAddress;
	@OneToMany(
		mappedBy = "location"
	)
	private List<Post> posts = new ArrayList();

}
