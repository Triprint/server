package com.triprint.backend.domain.image.dto;

import com.triprint.backend.domain.image.entity.Image;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
public class ImageDto {
    private Long id;
    private String path;

    @CreatedDate
    private Timestamp createdAt;

    @LastModifiedDate
    private Timestamp updatedAt;

    public static ImageDto of(Image image){
        return new ImageDto(image.getId(), image.getPath(), image.getCreatedAt(), image.getUpdatedAt());
    }

    public static List<ImageDto> listOf(List<Image> images) {
        return images.stream()
                .map(ImageDto::of)
                .collect(Collectors.toList());
    }
}
