package com.challenge.chat.exception.dto;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ChatErrorCode implements ErrorCode {

	CHATROOM_NOT_FOUND(HttpStatus.NOT_FOUND, "채팅방이 존재하지 않습니다"),
	KAFKA_PRODUCER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "채팅 전송에 실패했습니다"),
	KAFKA_CONSUMER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "채팅 수신에 실패했습니다"),
	SOCKET_CONNECTION_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "소켓 통신이 불안정 합니다"),
	;

	private final HttpStatus httpStatus;
	private final String message;
}
