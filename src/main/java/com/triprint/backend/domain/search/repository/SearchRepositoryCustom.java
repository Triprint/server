package com.triprint.backend.domain.search.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.triprint.backend.domain.location.entity.City;
import com.triprint.backend.domain.location.entity.District;
import com.triprint.backend.domain.post.entity.Post;
import com.triprint.backend.domain.search.dto.CurrentLocationRequest;

public interface SearchRepositoryCustom {
	Page<Post> findBySearchBasedOnCityAndDistrictKeywords(Pageable pageable, City city, District district);

	Page<Post> findBySearchBasedOnCityKeywords(Pageable pageable, City city);

	Page<Post> findByCurrentLocation(Pageable page, CurrentLocationRequest currentLocationRequest);
}
