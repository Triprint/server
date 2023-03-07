package com.triprint.backend.domain.location.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.triprint.backend.domain.location.entity.City;

public interface CityRepository extends JpaRepository<City, Long> {
	Optional<City> findByName(String name);
}
