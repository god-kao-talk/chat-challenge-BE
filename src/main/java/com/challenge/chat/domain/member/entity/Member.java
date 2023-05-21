package com.challenge.chat.domain.member.entity;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.challenge.chat.domain.member.constant.MemberRole;
import com.challenge.chat.domain.member.constant.SocialType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Builder
@AllArgsConstructor
public class Member {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "MEMBER_ID")
	private Long id;

	private String email; // 이메일
	private String password; // 비밀번호
	private String nickname; // 닉네임
	private String imageUrl; // 프로필 이미지

	@Enumerated(EnumType.STRING)
	private MemberRole role;

	@Enumerated(EnumType.STRING)
	private SocialType socialType; // KAKAO, NAVER, GOOGLE

	private String socialId; // 로그인한 소셜 타입의 식별자 값 (일반 로그인인 경우 null)

	private String refreshToken; // 리프레시 토큰
	// 유저 권한 설정 메소드
	public void authorizeUser() {
		this.role = MemberRole.USER;
	}

	// 비밀번호 암호화 메소드
	public void passwordEncode(PasswordEncoder passwordEncoder) {
		this.password = passwordEncoder.encode(this.password);
	}

	public void updateRefreshToken(String updateRefreshToken) {
		this.refreshToken = updateRefreshToken;
	}
}

