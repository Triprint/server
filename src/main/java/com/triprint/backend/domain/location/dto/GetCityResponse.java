package com.triprint.backend.domain.location.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.lang.NonNull;

import com.triprint.backend.domain.location.entity.City;
import com.triprint.backend.domain.location.entity.District;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GetCityResponse {
	@NonNull
	private Long id;

	private String name;

	private List<String> district = new ArrayList<>();

	public GetCityResponse(City city) {
		List<String> district = city.getDistrict().stream().map(District::getDistrictName).collect(Collectors.toList());

		this.id = city.getId();
		this.name = city.getName();
		this.district = district;
	}
}
