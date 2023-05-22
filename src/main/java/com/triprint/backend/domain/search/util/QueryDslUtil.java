package com.triprint.backend.domain.search.util;

import static org.springframework.util.ObjectUtils.*;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.Expressions;
import com.triprint.backend.domain.post.entity.QPost;

public class QueryDslUtil {

	public static List<OrderSpecifier> getAllOrderSpecifiers(Pageable pageable) {

		if (isEmpty(pageable.getSort())) {
			return new ArrayList<>();
		}

		List<OrderSpecifier> orders = new ArrayList<>();
		for (Sort.Order order : pageable.getSort()) {
			Order direction = order.getDirection().isAscending() ? Order.ASC : Order.DESC;
			String property = order.getProperty();
			OrderSpecifier<?> column = getSortedColumn(direction, QPost.post, property);
			orders.add(column);
		}
		return orders;
	}

	private static OrderSpecifier<?> getSortedColumn(Order order, Path<?> parent, String fieldName) {
		Path<Object> fieldPath = Expressions.path(Object.class, parent, fieldName);
		return new OrderSpecifier(order, fieldPath);
	}
}
