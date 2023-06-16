package com.challenge.chat.domain.member.entity;

import com.challenge.chat.domain.member.constant.MemberRole;
import com.challenge.chat.domain.member.constant.SocialType;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.cassandra.core.cql.Ordering;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@Table(value = "member")
@Getter
@Builder
@Slf4j
public class Member {
	@PrimaryKeyColumn(type = PrimaryKeyType.PARTITIONED)
	private String email; // 이메일
	@Column
	private String password; // 비밀번호
	@Column
	private String nickname; // 닉네임
	@Column(value = "image_url")
	private String imageUrl; // 프로필 이미지
	@Column
	private MemberRole role;
	@Column(value = "social_type")
	private SocialType socialType; // KAKAO, NAVER, GOOGLE
	@Column
	private String socialId; // 로그인한 소셜 타입의 식별자 값 (일반 로그인인 경우 null)
	@Column
	private String refreshToken; // 리프레시 토큰
	@Column
	private Instant createdAt;

	@Column
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;

	// @Column(value = "room_id_List")
	// private Set<String> roomIdList;

	public void updateRefreshToken(String updateRefreshToken) {
		log.info("리프레시 토큰 DB에 저장 완료");
		this.refreshToken = updateRefreshToken;
	}
}

