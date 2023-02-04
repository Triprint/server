package com.triprint.backend.domain.location.service;

import com.triprint.backend.domain.location.Repository.DistrictRepository;
import com.triprint.backend.domain.location.util.RoadNameAddress;
import com.triprint.backend.domain.location.entity.City;
import com.triprint.backend.domain.location.entity.District;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class DistrictService {

    private final DistrictRepository districtRepository;
    private final CityService cityService;

    @Transactional
    public District findOrCreate(String roadAddress){
        RoadNameAddress roadNameAddress = new RoadNameAddress(roadAddress);
        return districtRepository.findByDistrictName(roadNameAddress.getDistrict()).orElseGet(() -> this.createDistrict(roadNameAddress));
    }

    @Transactional
    public District createDistrict(RoadNameAddress roadNameAddress){
        City city = cityService.findOrCreate(roadNameAddress.getCity());
        District district = District.builder().districtName(roadNameAddress.getDistrict()).build();
        city.addDistrict(district);
        return districtRepository.save(district);
    }
}
