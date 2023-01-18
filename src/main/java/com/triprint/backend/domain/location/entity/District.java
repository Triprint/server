package com.triprint.backend.domain.location.entity;

import com.triprint.backend.domain.post.entity.Post;
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
public class District { //시군구 행정지역
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;
    private String districtCode;
    private String districtName;

    @OneToMany(
            mappedBy = "district"
    )
    private List<TouristAttraction> TouristAttraction = new ArrayList();

    @ManyToOne(
            fetch = FetchType.LAZY
    )
    @JoinColumn(
            name = "city_id"
    )
    private City city;

    @Builder
    public District(String districtCode, String districtName){
        this.districtCode = districtCode;
        this.districtName = districtName;
    }
}
