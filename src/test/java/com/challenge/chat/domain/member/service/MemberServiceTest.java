package com.challenge.chat.domain.member.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.exceptions.misusing.PotentialStubbingProblem;
import org.mockito.junit.jupiter.MockitoExtension;

import com.challenge.chat.domain.member.constant.MemberRole;
import com.challenge.chat.domain.member.constant.SocialType;
import com.challenge.chat.domain.member.dto.MemberDto;
import com.challenge.chat.domain.member.entity.Member;
import com.challenge.chat.domain.member.repository.MemberRepository;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

	@InjectMocks
	private MemberService memberService;

	@Mock //스프링빈에 등록이 안되는 가짜 객체
	private MemberRepository memberRepository;

	@Test
	@DisplayName("멤버 리스트 조회 성공")
	void getMemberList() {
		//given
		List<Member> memberList = new ArrayList<>();
		Member member1 = setMember(1L, "email1@test.com");
		Member member2 = setMember(2L, "email2@test.com");
		Member member3 = setMember(3L, "email3@test.com");
		memberList.add(member1);
		memberList.add(member2);
		memberList.add(member3);
		given(memberRepository.findAll()).willReturn(memberList);

		//when
		List<MemberDto> memberDtoList = memberService.getMemberList();

		//then
		for (int i = 0; i < memberDtoList.size(); i++) {
			assertThat(memberDtoList.get(i).getEmail()).isEqualTo(memberList.get(i).getEmail());
			assertThat(memberDtoList.get(i).getImageUrl()).isEqualTo(memberList.get(i).getImageUrl());
			assertThat(memberDtoList.get(i).getId()).isEqualTo(memberList.get(i).getId());
			assertThat(memberDtoList.get(i).getNickname()).isEqualTo(memberList.get(i).getNickname());
		}
	}

	@Test
	@DisplayName("email로 단일 멤버 조회 성공")
	void getMemberByEmail() {
		//given
		String email = "test1234@test.com";
		Member member = setMember(1L, email);
		given(memberRepository.findByEmail(email)).willReturn(Optional.of(member));

		//when
		MemberDto memberDtoList = memberService.getMemberByEmail(email);

		//then
		assertThat(memberDtoList.getEmail()).isEqualTo(member.getEmail());
		assertThat(memberDtoList.getImageUrl()).isEqualTo(member.getImageUrl());
		assertThat(memberDtoList.getId()).isEqualTo(member.getId());
		assertThat(memberDtoList.getNickname()).isEqualTo(member.getNickname());
	}

	@Test
	@DisplayName("email로 단일 멤버 조회 실패")
	void getMemberByEmailFail() {
		//given
		String email = "test1234@test.com";
		given(memberRepository.findByEmail(any())).willReturn(Optional.empty());

		//when, then
		assertThatThrownBy(() -> memberService.getMemberByEmail(email))
			.isInstanceOf(NoSuchElementException.class);
	}

	@Test
	@DisplayName("id로 정보 조회 성공")
	void getMemberByUserId() {
		//given
		long id = 1L;
		String email = "test1234@test.com";
		Member member = setMember(1L, email);
		given(memberRepository.findById(id)).willReturn(Optional.of(member));

		//when
		MemberDto resultMember = memberService.getMemberByUserId(id);

		//then
		assertThat(resultMember.getEmail()).isEqualTo(member.getEmail());
		assertThat(resultMember.getImageUrl()).isEqualTo(member.getImageUrl());
		assertThat(resultMember.getId()).isEqualTo(member.getId());
		assertThat(resultMember.getNickname()).isEqualTo(member.getNickname());
	}

	@Test
	@DisplayName("id로 정보 조회 실패")
	void getMemberByUserIdFail() {
		//given
		long id = 1L;
		given(memberRepository.findById(any())).willReturn(Optional.empty());

		//when, then
		assertThatThrownBy(() -> memberService.findMemberById(id))
			.isInstanceOf(NoSuchElementException.class);
	}

	private Member setMember(Long id, String email) {
		return new Member(id, email, "password", "nickName"
			, "image", MemberRole.USER, SocialType.GOOGLE, null
			, "refreshToken", null);
	}
}