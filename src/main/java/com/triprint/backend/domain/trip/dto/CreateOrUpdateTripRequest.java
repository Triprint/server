package com.triprint.backend.domain.trip.dto;

import java.util.ArrayList;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrUpdateTripRequest {
	private String title;
	private ArrayList<Long> posts = new ArrayList();
}
