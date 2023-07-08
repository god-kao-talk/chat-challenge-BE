package com.challenge.chat.domain.member.repository;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import com.challenge.chat.domain.member.constant.MemberRole;
import com.challenge.chat.domain.member.entity.Member;

@SpringJUnitConfig
@ActiveProfiles("test")
@SpringBootTest
public class MemberRepositoryTest {

	@Autowired
	private MemberRepository memberRepository;

	private Member getMember(String email, String password) {
		return Member.builder()
			.email(email)
			.password(password)
			.nickname(null)
			.imageUrl(null)
			.role(MemberRole.USER)
			.socialType(null)
			.socialId(null)
			.refreshToken(null)
			.build();
	}

	@Test
	@DisplayName("멤버 이메일로 조회하기")
	void findMemberByEmail() {
		//when
		final Member findMember = memberRepository.findByEmail("user@gmail.com").get();
		//then
		assertThat(findMember.getEmail()).isEqualTo("user@gmail.com");
	}
}
