package com.triprint.backend.domain.location.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.triprint.backend.domain.location.entity.District;

public interface DistrictRepository extends JpaRepository<District, Long> {
	Optional<District> findByDistrictName(String district);
}
