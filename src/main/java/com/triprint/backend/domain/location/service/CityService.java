package com.triprint.backend.domain.location.service;

import org.springframework.stereotype.Service;

import com.triprint.backend.domain.location.entity.City;
import com.triprint.backend.domain.location.repository.CityRepository;

import lombok.AllArgsConstructor;

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
