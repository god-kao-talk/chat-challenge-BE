package com.challenge.chat.global.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DefaultRes<T> {
	private String responseMessage;

	public DefaultRes(final String responseMessage) {
		this.responseMessage = responseMessage;
	}
}

