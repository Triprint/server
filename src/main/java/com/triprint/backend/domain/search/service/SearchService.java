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
import com.triprint.backend.domain.post.repository.PostRepository;
import com.triprint.backend.domain.search.dto.GetLocationRequest;
import com.triprint.backend.domain.search.repository.SearchRepositoryImpl;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SearchService {
	private final CityRepository cityRepository;
	private final DistrictRepository distRepository;
	private final SearchRepositoryImpl searchRepositoryimpl;
	private final PostRepository postRepository;

	public Page<GetPostResponse> keywordBasedSearch(Pageable page, GetLocationRequest getLocationRequest) {

		City city = cityRepository.findById(getLocationRequest.getCity().getId()).orElseThrow(() -> {
			throw new ResourceNotFoundException(ErrorMessage.CITY_NOT_FOUND);
		});
		District district = distRepository.findById(getLocationRequest.getDistrict().getId()).orElseThrow(() -> {
			throw new ResourceNotFoundException(ErrorMessage.DISTRICT_NOT_FOUND);
		});

		//TODO: 시군구가 없는 경우 city가 포함된 게시물 Page로 가져오기
		// 그 외는 city + district로 게시물 Page 가져오기
		if (district == null) {
			searchRepositoryimpl.findBySearchBasedOnCityKeywords(page, city);
		}
		searchRepositoryimpl.findBySearchBasedOnCityAndDistrictKeywords(page, city, district);

		return null;

	}

	public List<GetLocationRequest> getKeywordList() {
		List<City> cities = cityRepository.findAll();
		return makeKeywordList(cities);
	}

	private List<GetLocationRequest> makeKeywordList(List<City> cities) {
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
