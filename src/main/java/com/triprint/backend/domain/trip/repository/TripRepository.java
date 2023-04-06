package com.triprint.backend.domain.trip.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.triprint.backend.domain.trip.entity.Trip;

public interface TripRepository extends JpaRepository<Trip, Long> {
}
