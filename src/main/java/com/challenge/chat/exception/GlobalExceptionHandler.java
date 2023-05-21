package com.challenge.chat.exception;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.challenge.chat.exception.dto.CommonErrorCode;
import com.challenge.chat.exception.dto.ErrorCode;
import com.challenge.chat.exception.dto.ErrorResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler({RestApiException.class})
	public ResponseEntity<ErrorResponse> handleRestApiException(final RestApiException exception) {

		log.warn("RestApiException occur: ", exception);

		return this.makeErrorResponseEntity(exception.getErrorCode());
	}

	@ExceptionHandler({Exception.class})
	public ResponseEntity<ErrorResponse> handleException(final RestApiException exception) {

		log.warn("Exception occur: ", exception);

		return this.makeErrorResponseEntity(CommonErrorCode.INTERNAL_SERVER_ERROR);
	}

	// @Override
	// protected ResponseEntity<Object> handleMethodArgumentNotValid(
	// 	MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
	//
	// 	final List<String> errorList = ex.getBindingResult()
	// 		.getAllErrors()
	// 		.stream()
	// 		.map(DefaultMessageSourceResolvable::getDefaultMessage)
	// 		.collect(Collectors.toList());
	//
	// 	log.warn("Invalid Request Parameter errors : {}", errorList);
	//
	// 	return this.makeErrorResponseEntity(errorList.toString(), CommonErrorCode.INVALID_REQUEST_PARAMETER);
	// }

	private ResponseEntity<ErrorResponse> makeErrorResponseEntity(final ErrorCode errorCode) {

		return ResponseEntity
			.status(errorCode.getHttpStatus())
			.body(new ErrorResponse(errorCode.name(), errorCode.getHttpStatus().toString(), errorCode.getMessage()));
	}

	// private ResponseEntity<Object> makeErrorResponseEntity(final String errorDescription, final ErrorCode errorCode) {
	//
	// 	return ResponseEntity
	// 		.status(errorCode.getHttpStatus())
	// 		.body(new ErrorResponse(errorCode.name(), errorCode.getHttpStatus().toString(), errorDescription));
	// }
}
