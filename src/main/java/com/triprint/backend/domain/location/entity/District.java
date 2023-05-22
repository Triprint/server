package com.triprint.backend.domain.location.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.springframework.lang.NonNull;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class District {
	@Id
	@GeneratedValue(
		strategy = GenerationType.IDENTITY
	)
	private Long id;
	private String districtName;

	@OneToMany(
		mappedBy = "district", cascade = CascadeType.ALL
	)
	private List<TouristAttraction> touristAttractions = new ArrayList<>();

	@ManyToOne(
		fetch = FetchType.LAZY
	)
	@JoinColumn(
		name = "city_id"
	)
	@NonNull
	private City city;

	@Builder
	public District(String districtName) {
		this.districtName = districtName;
	}

	public void addTouristAttraction(TouristAttraction touristAttraction) {
		if (!this.touristAttractions.contains(touristAttraction)) {
			this.touristAttractions.add(touristAttraction);
			touristAttraction.setDistrict(this);
		}
		if (!touristAttraction.hasDistrict()) {
			touristAttraction.setDistrict(this);
		}
	}

	public void setCity(City city) {
		this.city = city;

		if (!city.getDistrict().contains(this)) {
			city.getDistrict().add(this);
		}
	}

	public boolean hasCity() {
		return Objects.nonNull(this.city);
	}
}
