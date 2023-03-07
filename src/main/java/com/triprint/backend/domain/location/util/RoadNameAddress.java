package com.triprint.backend.domain.location.util;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoadNameAddress {
	String district;
	String city;

	public RoadNameAddress(String roadName) {
		String[] beExtractedDistrict = roadName.split(" ");
		if (beExtractedDistrict[0].equals("세종특별자치시")) {
			this.district = beExtractedDistrict[0];
		} else {
			this.district = beExtractedDistrict[1];
		}
		this.city = beExtractedDistrict[0];
	}
}
