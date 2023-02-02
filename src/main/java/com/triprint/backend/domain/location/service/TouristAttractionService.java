package com.triprint.backend.domain.location.service;

import com.triprint.backend.domain.location.Repository.TouristAttractionRepository;
import com.triprint.backend.domain.location.dto.CreateTouristAttractionDto;
import com.triprint.backend.domain.location.entity.District;
import com.triprint.backend.domain.location.entity.TouristAttraction;
import lombok.AllArgsConstructor;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.io.WKTReader;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor
public class TouristAttractionService {

    private final TouristAttractionRepository touristAttractionRepository;
    private final DistrictService districtService;

    @Transactional
    public TouristAttraction findOrCreate(CreateTouristAttractionDto createTouristAttractionDto) throws Exception{
        Optional<TouristAttraction> touristAttraction = touristAttractionRepository.findByName(createTouristAttractionDto.getName());
        if(touristAttraction.isPresent()){
            return touristAttraction.get();
        }
        return this.createTouristAttraction(createTouristAttractionDto);
    }

    @Transactional
    public TouristAttraction createTouristAttraction(CreateTouristAttractionDto createTouristAttractionDto) throws Exception{
        Double x = createTouristAttractionDto.getX();
        Double y = createTouristAttractionDto.getY();
        String pointWKT = String.format("POINT(%s %s)", x, y);
        Point point = (Point) new WKTReader().read(pointWKT); //TODO: ParseError Handling
        District district = districtService.matchDistrict(createTouristAttractionDto.getRoadNameAddress());
        TouristAttraction touristAttraction = TouristAttraction.builder()
                .latitudeLongitude(point)
                .name(createTouristAttractionDto.getName())
                .roadNameAddress(createTouristAttractionDto.getRoadNameAddress())
                .build();
        district.addTouristAttraction(touristAttraction);

        return touristAttractionRepository.save(touristAttraction);
    }

    @Transactional
    public TouristAttraction updateTouristAttraction(CreateTouristAttractionDto updateTouristAttractionDto) throws Exception{
        TouristAttraction updateTouristAttraction = this.findOrCreate(updateTouristAttractionDto);
        return updateTouristAttraction;
    }


}
