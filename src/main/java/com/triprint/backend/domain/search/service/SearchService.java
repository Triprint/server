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
import com.triprint.backend.domain.post.repository.PostRepository;
import com.triprint.backend.domain.search.dto.CurrentLocationRequest;
import com.triprint.backend.domain.search.dto.CurrentLocationResponse;
import com.triprint.backend.domain.search.dto.GetLocationRequest;
import com.triprint.backend.domain.search.dto.GetLocationResponse;
import com.triprint.backend.domain.search.repository.SearchRepositoryImpl;
import com.triprint.backend.domain.search.util.LocationUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SearchService {
	private final CityRepository cityRepository;
	private final DistrictRepository distRepository;
	private final PostRepository postRepository;
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
		List<CurrentLocationResponse> currentLocationResponses = new ArrayList<>();
		List<Post> postList = postRepository.findAll();
		for (Post post : postList) {
			currentLocationResponses.add(CurrentLocationResponse.builder()
				.diff_Distance(LocationUtil.distance(post.getTouristAttraction().getLatitudeLongitude().getX(),
					post.getTouristAttraction().getLatitudeLongitude().getY(),
					Double.parseDouble(currentLocationRequest.getX()),
					Double.parseDouble(currentLocationRequest.getY()))
				).build());
		}
		int start = (int)page.getOffset();
		int end = Math.min((start + page.getPageSize()), currentLocationResponses.size());
		return new PageImpl<>(currentLocationResponses.subList(start, end), page, currentLocationResponses.size());
	}
}
