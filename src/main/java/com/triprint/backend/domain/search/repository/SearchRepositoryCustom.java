package com.triprint.backend.domain.search.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.triprint.backend.domain.post.entity.Post;

public interface SearchRepositoryCustom {
	Page<Post> findBySearchOption(Pageable pageable, String city, String district, String touristAttraction);
}
