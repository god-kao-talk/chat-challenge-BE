package com.challenge.chat.exception.validator;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target({FIELD})//필드 선언에만 사용될 수 있음을 나타냄
@Retention(RUNTIME)//해당 어노테이션이 유지되는 시간으로써 런타임까지 유효
@Constraint(validatedBy = PasswordValidator.class)//PhoeValidator를 통해 유효성 검사를 진행
@Documented
//JavaDoc 생성시 Annotation에 대한 정보도 함께 생성(@Documented가 붙은 어노테이션은 개발자가 API 문서를 작성할 때 해당 어노테이션에 대한 정보를 제공하는데 도움이 됨)
public @interface ValidPassword {

	String message() default "올바르지 않은 비밀번호 형식입니다";//유효성 검사가 실패했을 때 사용

	Class<?>[] groups() default {};//검사 그룹을 지정하는 데 사용

	Class<? extends Payload>[] payload() default {};//유효성 검사가 실패했을 때 연관된 추가 정보를 제공하는 데 사용
}
