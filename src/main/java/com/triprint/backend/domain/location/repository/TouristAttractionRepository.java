package com.triprint.backend.domain.location.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.triprint.backend.domain.location.entity.TouristAttraction;

public interface TouristAttractionRepository extends JpaRepository<TouristAttraction, Long> {
	Optional<TouristAttraction> findByName(String name);
}
