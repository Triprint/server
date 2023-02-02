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
    private String cityName;

    @OneToMany(
            mappedBy = "city", cascade = CascadeType.ALL
    )
    private List<District> district = new ArrayList();

    @Builder
    public City(String cityName){
        this.cityName = cityName;
    }

    public void addDistrict(District district) {
        if (!this.district.contains(district)){
            this.district.add(district);
            district.setCity(this);
        }
        if (!district.hasCity()){
            district.setCity(this);
        }
    }
}
