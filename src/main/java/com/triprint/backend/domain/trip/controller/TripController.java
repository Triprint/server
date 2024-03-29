package com.triprint.backend.domain.trip.controller;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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
import com.triprint.backend.domain.trip.dto.CreateOrUpdateTripRequest;
import com.triprint.backend.domain.trip.dto.GetMyTripResponse;
import com.triprint.backend.domain.trip.dto.GetTripResponse;
import com.triprint.backend.domain.trip.dto.TripResponse;
import com.triprint.backend.domain.trip.service.TripService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/trip")
public class TripController {

	private final TripService tripService;

	@GetMapping("/my")
	@PreAuthorize("hasRole('ROLE_USER')")
	public ResponseEntity<Page<GetMyTripResponse>> getMyTripList(@CurrentUser UserPrincipal userPrincipal,
		@PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable page) {
		return ResponseEntity.ok(tripService.getMyTripList(userPrincipal.getId(), page));
	}

	@PostMapping()
	@PreAuthorize("hasRole('ROLE_USER')")
	public ResponseEntity<TripResponse> createTrip(
		@Valid @RequestBody CreateOrUpdateTripRequest createOrUpdateTripRequest,
		@CurrentUser UserPrincipal userPrincipal) {
		return new ResponseEntity<>(tripService.create(userPrincipal.getId(), createOrUpdateTripRequest),
			HttpStatus.CREATED);
	}

	@GetMapping("/{id}")
	public ResponseEntity<GetTripResponse> getTrip(@CurrentUser UserPrincipal userPrincipal,
		@PathVariable Long id) {
		return new ResponseEntity<>(tripService.getTrip(id, userPrincipal), HttpStatus.OK);
	}

	@PutMapping("/{id}")
	@PreAuthorize("hasRole('ROLE_USER')")
	public ResponseEntity<TripResponse> updateTrip(
		@PathVariable Long id,
		@Valid @RequestBody CreateOrUpdateTripRequest createOrUpdateTripRequest,
		@CurrentUser UserPrincipal userPrincipal) {
		return new ResponseEntity<>(tripService.update(id, userPrincipal.getId(), createOrUpdateTripRequest),
			HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ROLE_USER')")
	public ResponseEntity<Void> deleteTrip(@PathVariable Long id, @CurrentUser UserPrincipal userPrincipal) {
		tripService.delete(id, userPrincipal.getId());
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
