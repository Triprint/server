package com.triprint.backend.domain.search.repository;

import static com.triprint.backend.domain.hashtag.entity.QHashtag.*;
import static com.triprint.backend.domain.location.entity.QDistrict.*;
import static com.triprint.backend.domain.location.entity.QTouristAttraction.*;
import static com.triprint.backend.domain.post.entity.QPost.*;
import static com.triprint.backend.domain.post.entity.QPostHashtag.*;
import static org.springframework.util.ObjectUtils.*;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.triprint.backend.core.exception.BadRequestException;
import com.triprint.backend.core.exception.ErrorMessage;
import com.triprint.backend.domain.location.entity.City;
import com.triprint.backend.domain.location.entity.District;
import com.triprint.backend.domain.post.entity.Post;
import com.triprint.backend.domain.post.entity.QPost;
import com.triprint.backend.domain.search.dto.CurrentLocationRequest;
import com.triprint.backend.domain.search.dto.PredictiveHashtagResponse;
import com.triprint.backend.domain.search.util.QueryDslUtil;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SearchRepositoryImpl implements SearchRepositoryCustom {
	private final JPAQueryFactory jpaQueryFactory;

	@Override
	public Page<Post> findBySearchBasedOnCityAndDistrictKeywords(Pageable pageable, City city, District district0) {

		try {
			List<OrderSpecifier> orders = getAllOrderSpecifiers(pageable);
			List<Post> result = (List<Post>)jpaQueryFactory.from(post)
				.join(post.touristAttraction, touristAttraction)
				.join(touristAttraction.district, district)
				.where(district.eq(district0), district.city.eq(city))
				.orderBy(orders.stream().toArray(OrderSpecifier[]::new))
				.offset(pageable.getOffset())
				.limit(pageable.getPageSize())
				.fetch();
			return new PageImpl<>(result);
		} catch (IllegalArgumentException queryException) {
			throw new BadRequestException(ErrorMessage.BAD_REQUEST);
		}
	}

	private List<OrderSpecifier> getAllOrderSpecifiers(Pageable pageable) {

		if (isEmpty(pageable.getSort())) {
			return new ArrayList<>();
		}

		List<OrderSpecifier> orders = new ArrayList<>();
		for (Sort.Order order : pageable.getSort()) {
			Order direction = order.getDirection().isAscending() ? Order.ASC : Order.DESC;
			String property = order.getProperty();
			OrderSpecifier<?> column = QueryDslUtil.getSortedColumn(direction, QPost.post, property);
			orders.add(column);
		}
		return orders;
	}

	@Override
	public Page<Post> findBySearchBasedOnCityKeywords(Pageable pageable, City city) {
		try {
			List<OrderSpecifier> orders = getAllOrderSpecifiers(pageable);
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
			).loe(String.valueOf(currentLocationRequest.getDistance())))
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

	/**
	 select *
	 from post
	 inner join post_hashtag
	 on post.id = post_hashtag.post_id
	 left join hashtag
	 on post_hashtag.hashtag_id = hashtag.id
	 where post_hashtag.hashtag_id = 1;
	 */
	@Override
	public Page<Post> findByHashtagPost(Pageable page, Long hashtagId) {
		List<OrderSpecifier> orderSpecifiers = getAllOrderSpecifiers(page);
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
