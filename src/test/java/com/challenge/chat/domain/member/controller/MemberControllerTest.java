package com.challenge.chat.domain.member.controller;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.challenge.chat.domain.chat.entity.MemberChatRoom;
import com.challenge.chat.domain.member.constant.MemberRole;
import com.challenge.chat.domain.member.constant.SocialType;
import com.challenge.chat.domain.member.dto.MemberDto;
import com.challenge.chat.domain.member.entity.Member;
import com.challenge.chat.domain.member.service.MemberService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;

@WithMockUser(username = "email")
@WebMvcTest(MemberController.class)
@MockBean(JpaMetamodelMappingContext.class)
class MemberControllerTest {

	@Autowired
	MockMvc mockMvc;

	@Autowired
	ObjectMapper objectMapper;

	@MockBean
	MemberService memberService;

	@Test
	@DisplayName("멤버 리스트 조회 서비스 호출 확인")
	void getMemberList() throws Exception {
		//given

		//when
		mockMvc.perform(get("/users"))
			.andExpect(status().isOk())
			.andReturn();

		//then
		verify(memberService, times(1)).getMemberList();
	}
	@Test
	@DisplayName("멤버 리스트 조회 서비스 호출 성공: JSON 확인")
	void getMemberListJSON() throws Exception {
		//given
		List<MemberDto> memberDtoList = new ArrayList<>();
		MemberDto memberDto1 = new MemberDto(setMember(1L, "email1"));
		MemberDto memberDto2 = new MemberDto(setMember(2L, "email2"));
		MemberDto memberDto3 = new MemberDto(setMember(3L, "email3"));
		memberDtoList.add(memberDto1);
		memberDtoList.add(memberDto2);
		memberDtoList.add(memberDto3);

		given(memberService.getMemberList()).willReturn(memberDtoList);

		//when, then
		MvcResult result =mockMvc.perform(get("/users"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.length()").value(3))
			.andReturn();

		String response = result.getResponse().getContentAsString();
		List<MemberDto> resultHashMap = JsonPath.read(response, "$.[*]");
		List<MemberDto> resultList = toMemberDto(resultHashMap);

		for (int i = 0; i < resultList.size(); i++) {
			assertThat(resultList.get(i).getId()).isEqualTo(memberDtoList.get(i).getId());
			assertThat(resultList.get(i).getNickname()).isEqualTo(memberDtoList.get(i).getNickname());
			assertThat(resultList.get(i).getEmail()).isEqualTo(memberDtoList.get(i).getEmail());
			assertThat(resultList.get(i).getImageUrl()).isEqualTo(memberDtoList.get(i).getImageUrl());
		}
	}

	@Test
	@DisplayName("멤버 email 조회 서비스 호출 확인")
	void getMemberByEmail() throws Exception {
		//given
		String email = "email";

		//when
		mockMvc.perform(get("/users/myinfo"))
			.andExpect(status().isOk())
			.andReturn();

		//then
		verify(memberService, times(1)).getMemberByEmail(email);
	}

	@Test
	@DisplayName("멤버 email 조회 서비스 호출 성공: JSON 확인")
	void getMemberByEmailJSON() throws Exception {
		//given
		String user = "email";
		MemberDto memberDto = new MemberDto(setMember(1L, user));
		given(memberService.getMemberByEmail(user)).willReturn(memberDto);

		//when, then
		MvcResult result =mockMvc.perform(get("/users/myinfo"))
			.andExpect(status().isOk())
			.andReturn();

		String response = result.getResponse().getContentAsString();
		Map<String, Object> dataString = JsonPath.parse(response).read("$");
		String body = toJson(dataString);
		MemberDto resultMemberDto = toMemberDto(body);

		assertThat(resultMemberDto.getId()).isEqualTo(memberDto.getId());
		assertThat(resultMemberDto.getNickname()).isEqualTo(memberDto.getNickname());
		assertThat(resultMemberDto.getEmail()).isEqualTo(memberDto.getEmail());
		assertThat(resultMemberDto.getImageUrl()).isEqualTo(memberDto.getImageUrl());
	}

	@Test
	@DisplayName("멤버 id 조회 서비스 호출 확인")
	void getMemberByUserId() throws Exception {
		//given
		long userId = 1L;
		//when
		mockMvc.perform(get("/users/" + userId))
			.andExpect(status().isOk())
			.andReturn();

		//then
		verify(memberService, times(1)).getMemberByUserId(userId);
	}

	@Test
	@DisplayName("멤버 id 조회 서비스 호출 성공: JSON 확인")
	void getMemberByUserIdJSON() throws Exception {
		//given
		long userId = 1L;
		MemberDto memberDto = new MemberDto(setMember(1L, "email"));
		given(memberService.getMemberByUserId(userId)).willReturn(memberDto);

		//when, then
		MvcResult result =mockMvc.perform(get("/users/"+userId))
			.andExpect(status().isOk())
			.andReturn();

		String response = result.getResponse().getContentAsString();
		Map<String, Object> dataString = JsonPath.parse(response).read("$");
		String body = toJson(dataString);
		MemberDto resultMemberDto = toMemberDto(body);

		assertThat(resultMemberDto.getId()).isEqualTo(memberDto.getId());
		assertThat(resultMemberDto.getNickname()).isEqualTo(memberDto.getNickname());
		assertThat(resultMemberDto.getEmail()).isEqualTo(memberDto.getEmail());
		assertThat(resultMemberDto.getImageUrl()).isEqualTo(memberDto.getImageUrl());
	}

	private Member setMember(Long id, String email) {
		List<MemberChatRoom> roomList = new ArrayList<>();
		return new Member(id, email, "password", "nickname",
			"imagerUrl", MemberRole.USER, SocialType.GOOGLE,
			"socialId", "refreshToken", roomList);
	}

	private <T> String toJson(T data) throws JsonProcessingException {
		return objectMapper.writeValueAsString(data);
	}

	private <T> MemberDto toMemberDto(String json) throws JsonProcessingException {
		return objectMapper.readValue(json , new TypeReference<MemberDto>(){});
	}

	private List<MemberDto> toMemberDto(List<MemberDto> resultHashMap) throws JsonProcessingException {
		return objectMapper.convertValue(resultHashMap , new TypeReference<List<MemberDto>>(){});
	}
}