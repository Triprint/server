package com.triprint.backend.domain.location.dto;

import javax.validation.constraints.NotNull;

import com.triprint.backend.domain.location.entity.TouristAttraction;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetTouristAttractionResponse {
	@NotNull
	private Long id;
	@SuppressWarnings("checkstyle:MemberName")
	@NotNull
	private String x;

	@SuppressWarnings("checkstyle:MemberName")
	@NotNull
	private String y;

	@NotNull
	private String roadNameAddress;

	@NotNull
	private String name;

	public GetTouristAttractionResponse(TouristAttraction touristAttraction) {
		this.id = touristAttraction.getId();
		this.x = String.valueOf(touristAttraction.getLatitudeLongitude().getY());
		this.y = String.valueOf(touristAttraction.getLatitudeLongitude().getX());
		this.roadNameAddress = touristAttraction.getRoadNameAddress();
		this.name = touristAttraction.getName();
	}
}
