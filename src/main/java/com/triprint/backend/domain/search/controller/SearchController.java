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
import com.triprint.backend.domain.search.dto.GetLocationRequest;
import com.triprint.backend.domain.search.dto.GetLocationResponse;
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

	// @GetMapping("/my/location")
	// public ResponseEntity<Page<GetPostResponse>> searchBasedOnCurrentLocation(
	// 	@PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable page,
	// 	CurrentLocationRequest currentLocationRequest) {
	// 	return ResponseEntity.ok(searchService.searchBasedOnCurrentLocation(page, currentLocationRequest));
	// }

}
