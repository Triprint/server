package com.triprint.backend.core.valid;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.triprint.backend.core.valid.enums.LocationType;

public class LocationValidator implements ConstraintValidator<ValidLocation, String> {
	LocationType locationType;

	@Override
	public void initialize(ValidLocation constraintAnnotation) {
		this.locationType = constraintAnnotation.locationType();
	}

	@Override
	public boolean isValid(String location, ConstraintValidatorContext context) {
		String latitudeAndLongitudeRegularExpression = "^[-+]?[0-9]+(?:.[0-9]+)?$";
		return location.matches(latitudeAndLongitudeRegularExpression) && isValidRange(location);
	}

	public boolean isValidRange(String location) {
		double ld = Double.parseDouble(location);
		if (this.locationType == LocationType.LONGITUDE) {
			return -180 <= ld && ld <= 180;
		}
		return -90 <= ld && ld <= 90;
	}
}
