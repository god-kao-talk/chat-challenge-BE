package com.challenge.chat.exception.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ErrorResponse {

	private final String errorCode;
	private final String status;
	private final String message;
}
