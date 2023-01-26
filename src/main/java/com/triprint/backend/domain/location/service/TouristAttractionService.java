package com.triprint.backend.domain.location.service;

import com.triprint.backend.domain.location.Repository.TouristAttractionRepository;
import com.triprint.backend.domain.post.entity.Post;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TouristAttractionService {

    private final TouristAttractionRepository touristAttractionRepository;

    public void createTouristAttraction(Post createdPost, Long x, Long y , String roadNameAddress, String firstDepthName, String secondDepthName){

    }

}
