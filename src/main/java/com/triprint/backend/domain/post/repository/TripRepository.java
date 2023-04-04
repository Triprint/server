package com.triprint.backend.domain.post.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.triprint.backend.domain.post.entity.Trip;

public interface TripRepository extends JpaRepository<Trip, Long> {
}
