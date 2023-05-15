package com.triprint.backend.domain.search.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.triprint.backend.domain.post.dto.GetPostResponse;
import com.triprint.backend.domain.search.dto.CurrentLocationRequest;
import com.triprint.backend.domain.search.dto.CurrentLocationResponse;
import com.triprint.backend.domain.search.dto.FindPostsWithHashtagRequest;
import com.triprint.backend.domain.search.dto.FindPostsWithHashtagResponse;
import com.triprint.backend.domain.search.dto.GetLocationRequest;
import com.triprint.backend.domain.search.dto.GetLocationResponse;
import com.triprint.backend.domain.search.dto.PredictiveHashtagRequest;
import com.triprint.backend.domain.search.dto.PredictiveHashtagResponse;
import com.triprint.backend.domain.search.service.SearchService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/search")
public class SearchController {
	private final SearchService searchService;

	@GetMapping()
	public ResponseEntity<List<GetLocationResponse>> getLocationList() {
		return ResponseEntity.ok(searchService.getLocationList());
	}

	@GetMapping("/location")
	public ResponseEntity<Page<GetPostResponse>> searchBasedOnLocation(
		@PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable page,
		GetLocationRequest getLocationRequest
	) {
		return ResponseEntity.ok(searchService.searchBasedOnLocation(page, getLocationRequest));
	}

	@GetMapping("/my/location")
	public ResponseEntity<Page<CurrentLocationResponse>> searchBasedOnCurrentLocation(
		@PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable page,
		CurrentLocationRequest currentLocationRequest) {
		return ResponseEntity.ok(searchService.searchBasedOnCurrentLocation(page, currentLocationRequest));
	}

	@GetMapping("/auto/hashtag")
	public ResponseEntity<List<PredictiveHashtagResponse>> getHashtag(
		PredictiveHashtagRequest predictiveHashtagRequest) {
		return ResponseEntity.ok(searchService.predictiveHashtag(predictiveHashtagRequest));
	}

	//해당 해시태그가 있는 포스트 검색 (Pagination O)
	@GetMapping("/auto/hashtag")
	public ResponseEntity<Page<FindPostsWithHashtagResponse>> findPostsWithHashtag(
		@PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable page,
		FindPostsWithHashtagRequest findPostsWithHashtagRequest) {
		return ResponseEntity.ok(searchService.findPostsWithHashtag(findPostsWithHashtagRequest));
	}
}
