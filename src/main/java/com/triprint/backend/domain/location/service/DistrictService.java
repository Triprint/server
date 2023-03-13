package com.triprint.backend.domain.location.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.triprint.backend.domain.location.entity.City;
import com.triprint.backend.domain.location.entity.District;
import com.triprint.backend.domain.location.repository.DistrictRepository;
import com.triprint.backend.domain.location.util.RoadNameAddress;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class DistrictService {

	private final DistrictRepository districtRepository;
	private final CityService cityService;

	@Transactional
	public District findOrCreate(String roadAddress) {
		RoadNameAddress roadNameAddress = new RoadNameAddress(roadAddress);
		return districtRepository.findByDistrictName(roadNameAddress.getDistrict())
			.orElseGet(() -> this.createDistrict(roadNameAddress));
	}

	@Transactional
	public District createDistrict(RoadNameAddress roadNameAddress) {
		City city = cityService.findOrCreate(roadNameAddress.getCity());
		District district = District.builder().districtName(roadNameAddress.getDistrict()).build();
		city.addDistrict(district);
		return districtRepository.save(district);
	}
}
