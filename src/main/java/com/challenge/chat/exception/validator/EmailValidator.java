package com.challenge.chat.exception.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EmailValidator implements ConstraintValidator<ValidEmail, String> {

	private static final String EMAIL_PATTERN = "^[a-zA-Z0-9._%-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		return value != null && value.matches(EMAIL_PATTERN);
	}
}