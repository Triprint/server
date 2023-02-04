package com.triprint.backend.domain.location.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@NoArgsConstructor
public class District { //시군구 행정지역
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;
    private String districtName;

    @OneToMany(
            mappedBy = "district", cascade = CascadeType.ALL
    )
    private List<TouristAttraction> touristAttractions = new ArrayList();

    @ManyToOne(
            fetch = FetchType.LAZY
    )
    @JoinColumn(
            name = "city_id"
    )
    private City city;

    @Builder
    public District(String districtName){
        this.districtName = districtName;
    }

    public void addTouristAttraction(TouristAttraction touristAttraction) {
        if (!this.touristAttractions.contains(touristAttraction)){
            this.touristAttractions.add(touristAttraction);
            touristAttraction.setDistrict(this);
        }
        if (!touristAttraction.hasDistrict()){
            touristAttraction.setDistrict(this);
        }
    }

    public void setCity(City city){
        this.city = city;

        if(!city.getDistrict().contains(this))
            city.getDistrict().add(this);
    }

    public boolean hasCity() {
        return Objects.nonNull(this.city);
    }
}
