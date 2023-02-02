package com.triprint.backend.domain.location.Repository;

import com.triprint.backend.domain.location.entity.District;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DistrictRepository extends JpaRepository<District, Long> {
    Optional<District> findByDistrictName(String district);
}
