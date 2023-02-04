package com.triprint.backend.domain.location.Repository;

import com.triprint.backend.domain.location.entity.TouristAttraction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TouristAttractionRepository extends JpaRepository<TouristAttraction, Long> {
    Optional<TouristAttraction> findByName(String name);
}
