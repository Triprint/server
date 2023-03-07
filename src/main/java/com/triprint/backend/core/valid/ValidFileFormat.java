package com.triprint.backend.core.valid;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR,
	ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {FileFormatValidator.class})
public @interface ValidFileFormat {
	String message() default "올바른 파일 형식이 아닙니다.";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

	String[] format() default {"image/jpg", "image/jpeg", "image/png"};
}
