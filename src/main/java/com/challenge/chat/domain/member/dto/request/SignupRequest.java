package com.challenge.chat.domain.member.dto.request;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SignupRequest {
	@NotBlank(message = "Email은 필수 값입니다.")
	private String email;

	@NotBlank(message = "Password는 필수 값입니다.")
	private String password;

	@NotBlank(message = "Nickname은 필수 값입니다.")
	private String nickname;
}
