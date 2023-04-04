package com.triprint.backend.domain.post.controller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.triprint.backend.domain.auth.security.CurrentUser;
import com.triprint.backend.domain.auth.security.UserPrincipal;
import com.triprint.backend.domain.post.dto.CreateOrUpdateTripRequest;
import com.triprint.backend.domain.post.dto.GetTripResponse;
import com.triprint.backend.domain.post.dto.TripResponse;
import com.triprint.backend.domain.post.service.TripService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/trip")
public class TripController {

	private final TripService tripService;

	@PostMapping()
	@PreAuthorize("hasRole('ROLE_USER')")
	public ResponseEntity<TripResponse> createPostGroup(
		@Valid @RequestBody CreateOrUpdateTripRequest createOrUpdateTripRequest,
		@CurrentUser UserPrincipal userPrincipal) {
		return new ResponseEntity<>(tripService.create(userPrincipal.getId(), createOrUpdateTripRequest),
			HttpStatus.CREATED);
	}

	@GetMapping("/{id}")
	public ResponseEntity<GetTripResponse> getPost(@CurrentUser UserPrincipal userPrincipal,
		@PathVariable Long id) {
		return new ResponseEntity<>(tripService.getTrip(id, userPrincipal), HttpStatus.OK);
	}

	@PutMapping("/{id}")
	@PreAuthorize("hasRole('ROLE_USER')")
	public ResponseEntity<TripResponse> updatePostGroup(
		@PathVariable Long id,
		@Valid @RequestBody CreateOrUpdateTripRequest createOrUpdateTripRequest,
		@CurrentUser UserPrincipal userPrincipal) {
		return new ResponseEntity<>(tripService.update(id, userPrincipal.getId(), createOrUpdateTripRequest),
			HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ROLE_USER')")
	public ResponseEntity<Void> deletePost(@PathVariable Long id, @CurrentUser UserPrincipal userPrincipal) {
		tripService.delete(id, userPrincipal.getId());
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
