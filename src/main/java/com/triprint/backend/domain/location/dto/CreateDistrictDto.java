package com.triprint.backend.domain.location.dto;

import com.triprint.backend.domain.location.entity.City;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateDistrictDto {
    String district;
    String city;
}