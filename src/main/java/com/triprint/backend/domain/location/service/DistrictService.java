package com.triprint.backend.domain.location.service;

import com.triprint.backend.domain.location.Repository.DistrictRepository;
import com.triprint.backend.domain.location.entity.District;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DistrictService {

    private final DistrictRepository districtRepository;

    public District matchDistrict(String roadAddress){
        String[] beExtractedDistrict = roadAddress.split(" ");
        String district = String.valueOf(districtRepository.findByDistrict(beExtractedDistrict[1]));
        if (district != null){
            return districtRepository.findByDistrict(beExtractedDistrict[1]);
        }
        if (beExtractedDistrict[0].equals("세종특별자치시")){
            return districtRepository.findByDistrict("세종특별자치시");
        }
        District newDistrict = District.builder()
                        .districtName(beExtractedDistrict[1])
                        .build();
        return districtRepository.save(newDistrict);
    }

}
