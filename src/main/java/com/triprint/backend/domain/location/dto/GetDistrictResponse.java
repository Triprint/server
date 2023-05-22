package com.triprint.backend.domain.location.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.lang.NonNull;

import com.triprint.backend.domain.location.entity.District;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GetDistrictResponse {
	@NonNull
	private Long id;

	private String name;

	private List<GetTouristAttractionResponse> touristAttractions;

	@NonNull
	private String cityName;

	public GetDistrictResponse(District district) {
		List<GetTouristAttractionResponse> touristAttractions = new ArrayList<>();
		district.getTouristAttractions()
			.forEach(dist -> touristAttractions.add(new GetTouristAttractionResponse(dist)));

		this.id = district.getId();
		this.name = district.getDistrictName();
		this.touristAttractions = touristAttractions;
		this.cityName = district.getCity().getName();
	}
}
