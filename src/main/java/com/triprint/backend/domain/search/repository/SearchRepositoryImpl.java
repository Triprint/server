package com.triprint.backend.domain.search.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.triprint.backend.domain.location.entity.City;
import com.triprint.backend.domain.location.entity.District;
import com.triprint.backend.domain.location.repository.CityRepository;
import com.triprint.backend.domain.location.repository.DistrictRepository;
import com.triprint.backend.domain.post.entity.Post;
import com.triprint.backend.domain.search.dto.GetLocationRequest;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SearchRepositoryImpl implements SearchRepositoryCustom {
	private final JPAQueryFactory jpaQueryFactory;
	private final CityRepository cityRepository;
	private final DistrictRepository districtRepository;

	public List<GetLocationRequest> findCityNDistricts() {
		List<City> cities = cityRepository.findAll();
		return makeCategory(cities);
	}

	private List<GetLocationRequest> makeCategory(List<City> cities) {
		List<GetLocationRequest> getLocationRequests = new ArrayList<>();

		cities.forEach((city) -> {
			List<District> districts = city.getDistrict();
			districts.forEach((district) -> {
				GetLocationRequest getLocationRequest = GetLocationRequest.builder()
					.city(city)
					.district(district)
					.build();
				getLocationRequests.add(getLocationRequest);
			});
			GetLocationRequest getLocationRequest = GetLocationRequest.builder()
				.city(city)
				.build();
			getLocationRequests.add(getLocationRequest);
		});
		return getLocationRequests;
	}

	@Override
	public Page<Post> findBySearchBasedOnCityAndDistrictKeywords(Pageable pageable, City city, District district) {

		return null;
	}

	@Override
	public Page<Post> findBySearchBasedOnCityKeywords(Pageable pageable, City city) {
		return null;
	}
}
