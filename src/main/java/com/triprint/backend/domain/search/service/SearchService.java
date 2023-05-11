package com.triprint.backend.domain.search.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
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
import com.triprint.backend.domain.search.dto.CurrentLocationRequest;
import com.triprint.backend.domain.search.dto.CurrentLocationResponse;
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
	private final SearchRepositoryImpl searchRepositoryimpl;

	public Page<GetPostResponse> searchBasedOnLocation(Pageable page, GetLocationRequest getLocationRequest) {

		City city = cityRepository.findById(getLocationRequest.getCityId()).orElseThrow(() -> {
			throw new ResourceNotFoundException(ErrorMessage.CITY_NOT_FOUND);
		});
		District district = distRepository.findById(getLocationRequest.getDistrictId()).orElseThrow(() -> {
			throw new ResourceNotFoundException(ErrorMessage.DISTRICT_NOT_FOUND);
		});

		if (district == null) {
			Page<Post> posts = searchRepositoryimpl.findBySearchBasedOnCityKeywords(page, city);
			return posts.map((post) -> new GetPostResponse(post, false));
		}
		Page<Post> posts = searchRepositoryimpl.findBySearchBasedOnCityAndDistrictKeywords(page, city, district);

		return posts.map((post) -> new GetPostResponse(post, false));
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
					.city(city)
					.district(district)
					.build();
				getLocationResponses.add(getLocationResponse);
			});
			GetLocationResponse getLocationResponse = GetLocationResponse.builder()
				.city(city)
				.build();
			getLocationResponses.add(getLocationResponse);
		});
		return getLocationResponses;
	}

	public Page<CurrentLocationResponse> searchBasedOnCurrentLocation(Pageable page,
		CurrentLocationRequest currentLocationRequest) {
		Page<Post> result = searchRepositoryimpl.findByCurrentLocation(page,
			currentLocationRequest);

		return getLocationResponses(result);
	}

	private Page<CurrentLocationResponse> getLocationResponses(Page<Post> posts) {
		List<CurrentLocationResponse> currentLocationResponses = new ArrayList<>();

		posts.forEach((post) -> {
			GetPostResponse getPostResponse = new GetPostResponse(post, false);
			CurrentLocationResponse currentLocationResponse = CurrentLocationResponse.builder()
				.id(getPostResponse.getId())
				.author(getPostResponse.getAuthor())
				.contents(getPostResponse.getContents())
				.title(getPostResponse.getTitle())
				.touristAttraction(getPostResponse.getTouristAttraction())
				.hashTags(getPostResponse.getHashtags())
				.createdAt(getPostResponse.getCreatedAt())
				.tripId(getPostResponse.getTripId())
				.images(getPostResponse.getImages())
				.likes(getPostResponse.getLikes())
				.build();
			currentLocationResponses.add(currentLocationResponse);
		});
		return new PageImpl<>(currentLocationResponses);
	}

	public PredictiveHashtagResponse predictiveHashtag(PredictiveHashtagRequest predictiveHashtagRequest) {

	}
}
