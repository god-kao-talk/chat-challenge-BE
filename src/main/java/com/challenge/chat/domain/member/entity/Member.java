package com.challenge.chat.domain.member.entity;

import com.challenge.chat.domain.chat.entity.MemberChatRoom;
import com.challenge.chat.domain.member.constant.MemberRole;
import com.challenge.chat.domain.member.constant.SocialType;
import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Builder
@AllArgsConstructor
@Slf4j
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

	@OneToMany(mappedBy = "member", orphanRemoval = true, cascade = CascadeType.ALL)
	private List<MemberChatRoom> roomList;

	public void updateRefreshToken(String updateRefreshToken) {
		log.info("리프레시 토큰 DB에 저장 완료");
		this.refreshToken = updateRefreshToken;
	}
}

