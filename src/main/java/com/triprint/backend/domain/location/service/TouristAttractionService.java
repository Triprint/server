package com.triprint.backend.domain.location.service;

import org.locationtech.jts.geom.Point;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKTReader;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.triprint.backend.core.exception.BadRequestException;
import com.triprint.backend.domain.location.dto.CreateTouristAttractionRequest;
import com.triprint.backend.domain.location.entity.District;
import com.triprint.backend.domain.location.entity.TouristAttraction;
import com.triprint.backend.domain.location.repository.TouristAttractionRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class TouristAttractionService {

	private final TouristAttractionRepository touristAttractionRepository;
	private final DistrictService districtService;

	@Transactional
	public TouristAttraction findOrCreate(CreateTouristAttractionRequest createTouristAttractionRequest) {
		return touristAttractionRepository.findByName(
				createTouristAttractionRequest.getName())
			.orElseGet(() -> this.createTouristAttraction(createTouristAttractionRequest));
	}

	@Transactional
	public TouristAttraction createTouristAttraction(CreateTouristAttractionRequest createTouristAttractionRequest) {
		try {
			String x = createTouristAttractionRequest.getX();
			String y = createTouristAttractionRequest.getY();
			String pointWkt = String.format("POINT(%s %s)", x, y);
			Point point = (Point)new WKTReader().read(pointWkt);
			District district = districtService.findOrCreate(createTouristAttractionRequest.getRoadNameAddress());
			TouristAttraction touristAttraction = TouristAttraction.builder()
				.latitudeLongitude(point)
				.name(createTouristAttractionRequest.getName())
				.roadNameAddress(createTouristAttractionRequest.getRoadNameAddress())
				.build();
			district.addTouristAttraction(touristAttraction);

			return touristAttractionRepository.save(touristAttraction);
		} catch (ParseException e) {
			throw new BadRequestException("올바른 위,경도 값이 아닙니다.");
		}
	}

	@Transactional
	public TouristAttraction updateTouristAttraction(CreateTouristAttractionRequest updateTouristAttractionDto) {
		return this.findOrCreate(updateTouristAttractionDto);
	}
}
