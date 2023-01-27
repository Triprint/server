package com.triprint.backend.domain.location.service;

import com.triprint.backend.domain.hashtag.entity.Hashtag;
import com.triprint.backend.domain.location.Repository.TouristAttractionRepository;
import com.triprint.backend.domain.location.dto.CreateTouristAttractionDto;
import com.triprint.backend.domain.location.entity.TouristAttraction;
import lombok.AllArgsConstructor;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.io.WKTReader;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class TouristAttractionService {

    private final TouristAttractionRepository touristAttractionRepository;
    private final DistrictService districtService;

    public TouristAttraction findOrCreate(CreateTouristAttractionDto createTouristAttractionDto) throws Exception{
        Optional<TouristAttraction> touristAttraction = touristAttractionRepository.findBytouristAttraction(createTouristAttractionDto.getTouristAttraction());
        if(touristAttraction.isPresent()){
            return touristAttraction.get();
        }
        return this.createTouristAttraction(createTouristAttractionDto);
    }

    public TouristAttraction createTouristAttraction(CreateTouristAttractionDto createTouristAttractionDto) throws Exception{
        Double latitude = createTouristAttractionDto.getX();
        Double longitude = createTouristAttractionDto.getY();
        String pointWKT = String.format("POINT(%s %s)", longitude, latitude);
        Point point = (Point) new WKTReader().read(pointWKT); //TODO: ParseError Handling
        districtService.matchDistrict(createTouristAttractionDto.getRoadNameAddress());

        return touristAttractionRepository.save(TouristAttraction.builder()
                .latitudeLongitude(point)
                .touristAttraction(createTouristAttractionDto.getTouristAttraction())
                .roadNameAddress(createTouristAttractionDto.getRoadNameAddress())
                .build());
    }

}
