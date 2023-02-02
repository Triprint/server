package com.triprint.backend.domain.location.service;

import com.triprint.backend.domain.location.Repository.CityRepository;
import com.triprint.backend.domain.location.entity.City;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CityService {

    private final CityRepository cityRepository;

    public City findOrCreate(String city) {
        return cityRepository.findByCityName(city).orElseGet(() -> createCity(city));
    }

    private City createCity(String city) {
        return cityRepository.save(City.builder().cityName(city).build());
    }
}
