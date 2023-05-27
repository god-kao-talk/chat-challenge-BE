package com.challenge.chat.domain.member.dto;

import com.challenge.chat.domain.member.entity.Member;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SignupDto {
	@NotBlank(message = "Email은 필수 값입니다.")
	private String email;

	@NotBlank(message = "Password는 필수 값입니다.")
	private String password;

	@NotBlank(message = "Nickname은 필수 값입니다.")
	private String nickname;

}
