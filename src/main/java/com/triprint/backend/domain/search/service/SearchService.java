package com.triprint.backend.domain.search.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.triprint.backend.core.exception.ErrorMessage;
import com.triprint.backend.core.exception.ResourceNotFoundException;
import com.triprint.backend.domain.hashtag.entity.Hashtag;
import com.triprint.backend.domain.hashtag.repository.HashtagRepository;
import com.triprint.backend.domain.location.dto.GetCityResponse;
import com.triprint.backend.domain.location.dto.GetDistrictResponse;
import com.triprint.backend.domain.location.entity.City;
import com.triprint.backend.domain.location.entity.District;
import com.triprint.backend.domain.location.repository.CityRepository;
import com.triprint.backend.domain.location.repository.DistrictRepository;
import com.triprint.backend.domain.post.dto.GetPostResponse;
import com.triprint.backend.domain.post.entity.Post;
import com.triprint.backend.domain.search.dto.CurrentLocationRequest;
import com.triprint.backend.domain.search.dto.GetLocationRequest;
import com.triprint.backend.domain.search.dto.GetLocationResponse;
import com.triprint.backend.domain.search.dto.PredictiveHashtagRequest;
import com.triprint.backend.domain.search.dto.PredictiveHashtagResponse;
import com.triprint.backend.domain.search.repository.SearchRepositoryImpl;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SearchService {
	private final CityRepository cityRepository;
	private final DistrictRepository distRepository;
	private final HashtagRepository hashtagRepository;
	private final SearchRepositoryImpl searchRepositoryimpl;

	public Page<com.triprint.backend.domain.post.dto.GetPostResponse> searchBasedOnLocation(Pageable page,
		GetLocationRequest getLocationRequest) {

		City city = cityRepository.findById(getLocationRequest.getCityId()).orElseThrow(() -> {
			throw new ResourceNotFoundException(ErrorMessage.CITY_NOT_FOUND);
		});

		Optional<Long> districtId = Optional.ofNullable(getLocationRequest.getDistrictId());

		if (districtId.isEmpty()) {
			Page<Post> posts = searchRepositoryimpl.findBySearchBasedOnCityKeywords(page, city);
			return posts.map((post) -> new com.triprint.backend.domain.post.dto.GetPostResponse(post, false));
		}

		District district = distRepository.findById(districtId.get()).orElseThrow(() -> {
			throw new ResourceNotFoundException(ErrorMessage.DISTRICT_NOT_FOUND);
		});

		Page<Post> posts = searchRepositoryimpl.findBySearchBasedOnCityAndDistrictKeywords(page, city, district);

		return posts.map((post) -> new com.triprint.backend.domain.post.dto.GetPostResponse(post, false));
	}

	public List<GetLocationResponse> getLocationList() {
		List<City> cities = cityRepository.findAll();
		return makeLocationList(cities);
	}

	private List<GetLocationResponse> makeLocationList(List<City> cities) {
		List<GetLocationResponse> getLocationResponses = new ArrayList<>();

		cities.forEach((city) -> {
			List<District> districts = city.getDistrict();
			districts.forEach((district) -> {
				GetLocationResponse getLocationResponse = GetLocationResponse.builder()
					.city(new GetCityResponse(city))
					.district(new GetDistrictResponse(district))
					.build();
				getLocationResponses.add(getLocationResponse);
			});
			GetLocationResponse getLocationResponse = GetLocationResponse.builder()
				.city(new GetCityResponse(city))
				.build();
			getLocationResponses.add(getLocationResponse);
		});
		return getLocationResponses;
	}

	public Page<GetPostResponse> searchBasedOnCurrentLocation(Pageable page,
		CurrentLocationRequest currentLocationRequest) {
		Page<Post> results = searchRepositoryimpl.findByCurrentLocation(page,
			currentLocationRequest);

		return getLocationResponses(results);
	}

	private Page<GetPostResponse> getLocationResponses(Page<Post> posts) {
		List<GetPostResponse> getPostResponses = new ArrayList<>();

		posts.forEach((post) -> {
			GetPostResponse getPostResponse = new GetPostResponse(post, false);
			getPostResponses.add(getPostResponse);
		});
		return new PageImpl<>(getPostResponses);
	}

	public List<PredictiveHashtagResponse> predictiveHashtag(PredictiveHashtagRequest predictiveHashtagRequest) {
		List<PredictiveHashtagResponse> predictiveHashtagResponse = searchRepositoryimpl.findByHashtag(
			predictiveHashtagRequest.getKeyword());
		return predictiveHashtagResponse;
	}

	public Page<GetPostResponse> findPostsWithHashtag(Pageable page,
		Long id) {
		Hashtag hashtag = hashtagRepository.findById(id).orElseThrow(() -> {
			throw new ResourceNotFoundException(ErrorMessage.HASHTAG_NOT_FOUND);
		});

		Page<Post> posts = searchRepositoryimpl.findByHashtagPost(page,
			id);

		return getLocationResponses(posts);
	}
}
