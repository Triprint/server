package com.triprint.backend.domain.search.repository;

import static com.triprint.backend.domain.location.entity.QDistrict.*;
import static com.triprint.backend.domain.location.entity.QTouristAttraction.*;
import static com.triprint.backend.domain.post.entity.QPost.*;
import static org.springframework.util.ObjectUtils.*;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.querydsl.core.QueryException;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.triprint.backend.core.exception.ResourceNotFoundException;
import com.triprint.backend.domain.location.entity.City;
import com.triprint.backend.domain.location.entity.District;
import com.triprint.backend.domain.post.entity.Post;
import com.triprint.backend.domain.post.entity.QPost;
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
		} catch (QueryException queryException) {
			throw new ResourceNotFoundException("해당하는 검색키워드가 없습니다.");
		}
	}

	/**
	 * Order의 칼럼이 없는 경우
	 * 1. 값 무시 -> 메서드 만들어서
	 * 2. 에러 터뜨리기 -> 에러가 터지면 나만의 에러로 핸들링하기
	 *    클래스에 존재하는 칼럼인지 확인하는 메서드가 있는지 확인하기
	 */

	private List<OrderSpecifier> getAllOrderSpecifiers(Pageable pageable) {

		if (isEmpty(pageable.getSort())) {
			return new ArrayList<>();
		}

		List<OrderSpecifier> orders = new ArrayList<>();
		for (Sort.Order order : pageable.getSort()) {
			Order direction = order.getDirection().isAscending() ? Order.ASC : Order.DESC;
			String property = order.getProperty();
			Field[] existColumns = Post.class.getDeclaredFields();
			String[] fieldName = Arrays.stream(existColumns)
				.map(Object::toString)
				.toArray(String[]::new);

			// 만약에 Post에 없는 Column이 요청이으로 들어왔을 때?
			if (Arrays.stream(fieldName).anyMatch(property::equals)) {
				OrderSpecifier<?> column = QueryDslUtil.getSortedColumn(direction, QPost.post, property);
				orders.add(column);
			}
			// OrderSpecifier<?> column = QueryDslUtil.getSortedColumn(direction, QPost.post, property);
			// orders.add(column);

			// switch (order.getProperty()) {
			// 	case "id":
			// 		OrderSpecifier<?> orderId = QueryDslUtil.getSortedColumn(direction, QPost.post, "id");
			// 		orders.add(orderId);
			// 		break;
			// 	case "createdAt":
			// 		OrderSpecifier<?> orderCreatedAt = QueryDslUtil.getSortedColumn(direction, QPost.post, "createdAt");
			// 		orders.add(orderCreatedAt);
			// 		break;
			// 	default:
			// 		break;
			// }
		}
		return orders;
	}

	@Override
	public Page<Post> findBySearchBasedOnCityKeywords(Pageable pageable, City city) {
		return null;
	}
}
