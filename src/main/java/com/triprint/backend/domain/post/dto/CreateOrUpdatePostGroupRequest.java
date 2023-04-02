package com.triprint.backend.domain.post.dto;

import java.util.ArrayList;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrUpdatePostGroupRequest {
	private String title;
	private ArrayList<Long> posts = new ArrayList();
}
