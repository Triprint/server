package com.triprint.backend.core.valid;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.annotation.Nullable;
import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.Min;

import org.hibernate.validator.constraints.CompositionType;
import org.hibernate.validator.constraints.ConstraintComposition;

@Min(1)
@Nullable()
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {})
@ConstraintComposition(CompositionType.OR)
public @interface ValidIdNullable {
	String message() default "올바르지 않은 ID 값입니다.";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
