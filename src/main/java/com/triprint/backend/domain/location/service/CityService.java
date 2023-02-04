package com.triprint.backend.domain.location.service;

import com.triprint.backend.domain.location.Repository.CityRepository;
import com.triprint.backend.domain.location.entity.City;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CityService {

    private final CityRepository cityRepository;

    public City findOrCreate(String name) {
        return cityRepository.findByName(name).orElseGet(() -> createCity(name));
    }

    private City createCity(String name) {
        return cityRepository.save(City.builder().name(name).build());
    }
}
