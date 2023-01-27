package com.triprint.backend.domain.location.Repository;

import com.triprint.backend.domain.location.entity.District;
import com.triprint.backend.domain.location.entity.TouristAttraction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DistrictRepository extends JpaRepository<District, Long> {
    District findByDistrict(String district);
}
