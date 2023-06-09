package com.challenge.chat.exception.dto;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MemberErrorCode implements ErrorCode {

	MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "회원을 찾을 수 없습니다"),
	DUPLICATED_EMAIL(HttpStatus.BAD_REQUEST, "이미 존재하는 email 입니다"),
	DUPLICATED_MEMBER(HttpStatus.BAD_REQUEST, "이미 추가된 친구 입니다")
	;

	private final HttpStatus httpStatus;
	private final String message;
}
