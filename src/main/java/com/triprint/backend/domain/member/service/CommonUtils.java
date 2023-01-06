package com.triprint.backend.domain.member.service;

import java.util.UUID;

public class CommonUtils {

	private static final String FILE_EXTENSION_SEPARATOR = ".";
	private static final String CATEGORY_PREFIX = "/";

	public static String buildFileName(String category, String originalFileName) {
		int fileExtensionIndex = originalFileName.lastIndexOf(FILE_EXTENSION_SEPARATOR);
		String fileExtension = originalFileName.substring(fileExtensionIndex);

		UUID uuid = UUID.randomUUID();

		return category + CATEGORY_PREFIX + uuid + fileExtension;
	}

}
