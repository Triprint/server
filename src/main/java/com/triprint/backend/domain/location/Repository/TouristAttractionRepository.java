package com.triprint.backend.domain.location.Repository;

import com.triprint.backend.domain.location.entity.TouristAttraction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TouristAttractionRepository extends JpaRepository<TouristAttraction, Long> {
}
