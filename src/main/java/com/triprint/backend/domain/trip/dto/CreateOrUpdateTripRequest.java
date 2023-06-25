package com.triprint.backend.domain.trip.dto;

import java.util.ArrayList;

import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrUpdateTripRequest {
	@Nullable
	private String title;

	@NotNull
	private ArrayList<Long> posts = new ArrayList();
}
