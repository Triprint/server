package com.triprint.backend.domain.member.service;

import java.util.UUID;

public class CommonUtils {

	private static final String FILE_EXTENSION_SEPARATOR = ".";
	private static final String CATEGORY_PREFIX = "/";

	public static String buildFileName(String category, String originalFileName) {
		int fileExtensionIndex = originalFileName.lastIndexOf(FILE_EXTENSION_SEPARATOR);	// 파일확장자 구분선
		String fileExtension = originalFileName.substring(fileExtensionIndex);	// 파일확장자
		// String fileName = originalFileName.substring(0, fileExtensionIndex);		// 파일명
		// String now = String.valueOf(System.currentTimeMillis());				// 업로드시간

		UUID uuid = UUID.randomUUID();

		return category + CATEGORY_PREFIX + uuid + fileExtension;
	}

}
