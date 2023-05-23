package com.triprint.backend.domain.trip.dto;

import java.util.ArrayList;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrUpdateTripRequest {
	@Nullable
	private String title;

	@NonNull
	private ArrayList<Long> posts = new ArrayList();
}
