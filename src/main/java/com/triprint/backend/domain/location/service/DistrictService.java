package com.triprint.backend.domain.location.service;

import com.triprint.backend.domain.hashtag.entity.Hashtag;
import com.triprint.backend.domain.location.Repository.DistrictRepository;
import com.triprint.backend.domain.location.dto.CreateDistrictDto;
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
    public District matchDistrict(String roadAddress){
        String[] beExtractedDistrict = roadAddress.split(" ");
        CreateDistrictDto createDistrictDto = CreateDistrictDto.builder()
                .city(beExtractedDistrict[0])
                .district(beExtractedDistrict[1])
                .build();

        if (beExtractedDistrict[0].equals("세종특별자치시")){
            return districtRepository.findByDistrictName("세종특별자치시").orElseThrow(NullPointerException::new); //Exception 추가 후 변경
        }
        return this.findOrCreate(createDistrictDto);
    }

    public District findOrCreate(CreateDistrictDto createDistrictDto){
        return districtRepository.findByDistrictName(createDistrictDto.getDistrict()).orElseGet(() -> this.createDistrict(createDistrictDto));
    }

    public District createDistrict(CreateDistrictDto createDistrictDto){
        City city = cityService.findOrCreate(createDistrictDto.getCity());
        District district = District.builder().districtName(createDistrictDto.getDistrict()).build();
        city.addDistrict(district);
        return districtRepository.save(district);
    }
}
