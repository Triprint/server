package com.triprint.backend.domain.search.repository;

import static com.triprint.backend.domain.hashtag.entity.QHashtag.*;
import static com.triprint.backend.domain.location.entity.QDistrict.*;
import static com.triprint.backend.domain.location.entity.QTouristAttraction.*;
import static com.triprint.backend.domain.post.entity.QPost.*;
import static com.triprint.backend.domain.post.entity.QPostHashtag.*;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.triprint.backend.core.exception.BadRequestException;
import com.triprint.backend.core.exception.ErrorMessage;
import com.triprint.backend.domain.location.entity.City;
import com.triprint.backend.domain.location.entity.District;
import com.triprint.backend.domain.post.entity.Post;
import com.triprint.backend.domain.search.dto.CurrentLocationRequest;
import com.triprint.backend.domain.search.dto.PredictiveHashtagResponse;
import com.triprint.backend.domain.search.util.QueryDslUtil;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SearchRepositoryImpl implements SearchRepositoryCustom {
	private final JPAQueryFactory jpaQueryFactory;
	private static final int KM = 1000;

	@Override
	public Page<Post> findBySearchBasedOnCityAndDistrictKeywords(Pageable pageable, City city, District pDistrict) {

		try {
			List<OrderSpecifier> orders = QueryDslUtil.getAllOrderSpecifiers(pageable);
			List<Post> result = (List<Post>)jpaQueryFactory.from(post)
				.join(post.touristAttraction, touristAttraction)
				.join(touristAttraction.district, district)
				.where(district.eq(pDistrict), district.city.eq(city))
				.orderBy(orders.stream().toArray(OrderSpecifier[]::new))
				.offset(pageable.getOffset())
				.limit(pageable.getPageSize())
				.fetch();
			return new PageImpl<>(result);
		} catch (IllegalArgumentException queryException) {
			throw new BadRequestException(ErrorMessage.BAD_REQUEST);
		}
	}

	@Override
	public Page<Post> findBySearchBasedOnCityKeywords(Pageable pageable, City city) {
		try {
			List<OrderSpecifier> orders = QueryDslUtil.getAllOrderSpecifiers(pageable);
			List<Post> result = (List<Post>)jpaQueryFactory.from(post)
				.join(post.touristAttraction, touristAttraction)
				.join(touristAttraction.district, district)
				.where(district.city.eq(city))
				.orderBy(orders.stream().toArray(OrderSpecifier[]::new))
				.offset(pageable.getOffset())
				.limit(pageable.getPageSize())
				.fetch();
			return new PageImpl<>(result);
		} catch (IllegalArgumentException queryException) {
			throw new BadRequestException(ErrorMessage.BAD_REQUEST);
		}
	}

	@Override
	public Page<Post> findByCurrentLocation(Pageable page, CurrentLocationRequest currentLocationRequest) {

		List<Post> posts = jpaQueryFactory
			.selectFrom(post)
			.join(post.touristAttraction, touristAttraction)
			.where(Expressions.stringTemplate("ST_Distance_Sphere({0}, {1})",
				Expressions.stringTemplate("POINT({0}, {1})",
					currentLocationRequest.getX(),
					currentLocationRequest.getY()
				),
				Expressions.stringTemplate("{0}",
					post.touristAttraction.latitudeLongitude
				)
			).loe(String.valueOf(currentLocationRequest.getDistance() * KM)))
			.limit(page.getPageSize())
			.offset(page.getOffset())
			.fetch();
		return new PageImpl<>(posts);
	}

	@Override
	public List<PredictiveHashtagResponse> findByHashtag(String keyword) {

		List<PredictiveHashtagResponse> hashtags = jpaQueryFactory.select(
				Projections.constructor(PredictiveHashtagResponse.class, hashtag.id.as("tagId"),
					hashtag.contents.as("tagName"),
					postHashtag.post.id.count().as("postCnt")))
			.from(postHashtag)
			.leftJoin(postHashtag.hashtag, hashtag)
			.where(hashtag.contents.contains(keyword))
			.groupBy(hashtag.id)
			.orderBy(postHashtag.post.id.count().desc())
			.fetch();
		return hashtags;
	}

	@Override
	public Page<Post> findByHashtagPost(Pageable page, Long hashtagId) {
		List<OrderSpecifier> orderSpecifiers = QueryDslUtil.getAllOrderSpecifiers(page);
		List<Post> posts = jpaQueryFactory.selectFrom(post)
			.innerJoin(post.postHashtag, postHashtag)
			.leftJoin(postHashtag.hashtag, hashtag)
			.where(hashtag.id.eq(hashtagId))
			.offset(page.getOffset())
			.limit(page.getPageSize())
			.orderBy(orderSpecifiers.stream().toArray(OrderSpecifier[]::new))
			.fetch();
		return new PageImpl<>(posts);
	}
}
