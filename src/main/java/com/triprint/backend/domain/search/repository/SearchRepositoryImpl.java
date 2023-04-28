package com.triprint.backend.domain.search.repository;

import static com.triprint.backend.domain.post.entity.QPost.*;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.triprint.backend.domain.location.entity.City;
import com.triprint.backend.domain.location.entity.District;
import com.triprint.backend.domain.location.repository.CityRepository;
import com.triprint.backend.domain.location.repository.DistrictRepository;
import com.triprint.backend.domain.post.entity.Post;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SearchRepositoryImpl implements SearchRepositoryCustom {
	private final JPAQueryFactory jpaQueryFactory;
	private final CityRepository cityRepository;
	private final DistrictRepository districtRepository;

	@Override
	public Page<Post> findBySearchBasedOnCityAndDistrictKeywords(Pageable pageable, City city, District district) {
		List<Post> result = jpaQueryFactory.selectFrom(post)
			.where(
				post.touristAttraction.district.city.eq(city).and(post.touristAttraction.district.eq(district))
			).fetch();

		return new PageImpl<>(result);
	}

	@Override
	public Page<Post> findBySearchBasedOnCityKeywords(Pageable pageable, City city) {
		return null;
	}
}
