package com.triprint.backend.domain.trip.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.triprint.backend.domain.trip.entity.Trip;
import com.triprint.backend.domain.user.entity.User;

public interface TripRepository extends JpaRepository<Trip, Long> {

	@Query("select t from Trip t where t.user = :user")
	Page<Trip> findByTripUser(@Param("user") User user, Pageable page);
}
