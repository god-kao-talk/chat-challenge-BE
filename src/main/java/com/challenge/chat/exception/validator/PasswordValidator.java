package com.challenge.chat.exception.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<ValidPassword, String> {
	//ConstraintValidator 인터페이스는 Bean Validation API의 일부로, 개발자가 커스텀 제약 조건을 정의할 수 있게 해주는 인터페이스
	private static final String PASSWORD_PATTERN = "^[^\\s]{4,8}$";
	private static final Pattern UPPERCASE_PATTERN = Pattern.compile("[A-Z]");

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		boolean isValid = true;
		context.disableDefaultConstraintViolation();

		if (value == null || !value.matches(PASSWORD_PATTERN)) {
			context.buildConstraintViolationWithTemplate("비밀번호는 4자 이상 8자 이하로 작성해주세요.")
				.addConstraintViolation();
			isValid = false;
		}

		//value는 ConstraintValidator<ValidPassword, String> 인터페이스의 isValid 메서드의 첫 번째 파라미터 즉 ValidPassword어노테이션이 붙은 String필드
		//Matcher 클래스는 주어진 Pattern 객체를 이용해서 특정 문자열에 대한 매칭 작업을 수행
		Matcher m = UPPERCASE_PATTERN.matcher(value);
		if (m.find()) {
			context.buildConstraintViolationWithTemplate("비밀번호에는 대문자를 사용할 수 없습니다.")
				.addConstraintViolation();
			isValid = false;
		}

		return isValid;
	}
}