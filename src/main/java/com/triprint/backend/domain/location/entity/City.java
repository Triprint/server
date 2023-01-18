package com.triprint.backend.domain.location.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Point;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class City { //시도 행정지역
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;
    private String cityCode;
    private String cityName;

    @OneToMany(
            mappedBy = "city"
    )
    private List<District> district = new ArrayList();

    @Builder
    public City(String cityCode, String cityName){
        this.cityCode = cityCode;
        this.cityName = cityName;
    }
}
