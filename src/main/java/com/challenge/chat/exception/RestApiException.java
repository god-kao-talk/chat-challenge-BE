package com.challenge.chat.exception;

import com.challenge.chat.exception.dto.ErrorCode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class RestApiException extends RuntimeException {

	private final ErrorCode errorCode;
}
