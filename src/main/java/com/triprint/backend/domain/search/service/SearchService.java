package com.triprint.backend.domain.search.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.triprint.backend.core.exception.ErrorMessage;
import com.triprint.backend.core.exception.ResourceNotFoundException;
import com.triprint.backend.domain.location.entity.City;
import com.triprint.backend.domain.location.entity.District;
import com.triprint.backend.domain.location.repository.CityRepository;
import com.triprint.backend.domain.location.repository.DistrictRepository;
import com.triprint.backend.domain.post.dto.GetPostResponse;
import com.triprint.backend.domain.post.entity.Post;
import com.triprint.backend.domain.search.dto.GetLocationRequest;
import com.triprint.backend.domain.search.repository.SearchRepositoryImpl;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SearchService {
	private final CityRepository cityRepository;
	private final DistrictRepository distRepository;
	private final SearchRepositoryImpl searchRepositoryimpl;

	public Page<GetPostResponse> searchBasedOnLocation(Pageable page, GetLocationRequest getLocationRequest) {

		City city = cityRepository.findById(getLocationRequest.getCity().getId()).orElseThrow(() -> {
			throw new ResourceNotFoundException(ErrorMessage.CITY_NOT_FOUND);
		});
		District district = distRepository.findById(getLocationRequest.getDistrict().getId()).orElseThrow(() -> {
			throw new ResourceNotFoundException(ErrorMessage.DISTRICT_NOT_FOUND);
		});
		
		Page<Post> posts = searchRepositoryimpl.findBySearchBasedOnCityAndDistrictKeywords(page, city, district);
		if (district == null) {
			posts = searchRepositoryimpl.findBySearchBasedOnCityKeywords(page, city);
		}

		return posts.map((post) -> new GetPostResponse(post, false));
	}

	public List<GetLocationRequest> getLocationList() {
		List<City> cities = cityRepository.findAll();
		return makeLocationList(cities);
	}

	private List<GetLocationRequest> makeLocationList(List<City> cities) {
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
}
