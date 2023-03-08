package com.triprint.backend.core.valid;

import java.util.Arrays;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.web.multipart.MultipartFile;

public class FileFormatValidator implements ConstraintValidator<ValidFileFormat, List<MultipartFile>> {

	private List<String> validFormats;

	@Override
	public void initialize(ValidFileFormat constraintAnnotation) {
		this.validFormats = Arrays.asList(constraintAnnotation.format());
	}

	@Override
	public boolean isValid(List<MultipartFile> multipartFiles, ConstraintValidatorContext context) {

		for (MultipartFile file : multipartFiles) {
			if (!isValidFormat(file)) {
				return false;
			}
		}
		return true;
	}

	private boolean isValidFormat(MultipartFile file) {
		return this.validFormats.contains(file.getContentType());
	}

}
