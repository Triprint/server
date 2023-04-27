package com.triprint.backend.domain.search.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.triprint.backend.core.exception.ErrorMessage;
import com.triprint.backend.core.exception.ResourceNotFoundException;
import com.triprint.backend.domain.location.dto.GetLocationRequest;
import com.triprint.backend.domain.location.entity.City;
import com.triprint.backend.domain.location.entity.District;
import com.triprint.backend.domain.location.repository.CityRepository;
import com.triprint.backend.domain.post.dto.GetPostResponse;
import com.triprint.backend.domain.post.entity.Post;
import com.triprint.backend.domain.post.repository.PostRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SearchService {
	private final CityRepository cityRepository;
	private final PostRepository postRepository;

	public Page<GetPostResponse> getResult(Pageable page, GetLocationRequest getLocationRequest) {

		City city = cityRepository.findById(id).orElseThrow(() -> {
			throw new ResourceNotFoundException(ErrorMessage.CITY_NOT_FOUND);
		});

		//TODO: QueryDSL사용하여 해결해야할 것 같음. (POST와 City는 직접적인 연관관계가 없으므로 아래와 같은 방식은 적용할 수 없음.)
		Page<Post> posts = postRepository.findByCity(city, page);
		return posts.map((post) -> new GetPostResponse(post, false));

	}

	public List<GetLocationRequest> getCategory() {
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
}
