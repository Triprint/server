package com.triprint.backend.core.valid;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.triprint.backend.core.valid.enums.LocationType;

@Constraint(validatedBy = LocationValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidLocation {
	String message() default "올바른 위,경도를 입력해주세요";

	LocationType locationType();

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
