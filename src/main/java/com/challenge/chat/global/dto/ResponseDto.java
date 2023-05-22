package com.challenge.chat.global.dto;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor(staticName = "set")
public class ResponseDto<T> {
	private HttpStatus status;
	private String message;
	private T data;

	public static <T> ResponseDto<T> setSuccess(String message, T data) {
		return ResponseDto.set(HttpStatus.OK, message, data);
	}

	public static <T> ResponseDto<T> setBadRequest(String message) {
		return ResponseDto.set(HttpStatus.BAD_REQUEST, message, null);
	}
}

