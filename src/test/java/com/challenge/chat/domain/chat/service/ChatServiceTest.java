package com.challenge.chat.domain.chat.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;

import com.challenge.chat.domain.chat.dto.ChatDto;
import com.challenge.chat.domain.chat.dto.ChatRoomDto;
import com.challenge.chat.domain.chat.dto.EnterUserDto;
import com.challenge.chat.domain.chat.entity.Chat;
import com.challenge.chat.domain.chat.entity.ChatRoom;
import com.challenge.chat.domain.chat.entity.MemberChatRoom;
import com.challenge.chat.domain.chat.entity.MessageType;
import com.challenge.chat.domain.chat.repository.ChatRepository;
import com.challenge.chat.domain.chat.repository.ChatRoomRepository;
import com.challenge.chat.domain.chat.repository.MemberChatRoomRepository;
import com.challenge.chat.domain.member.constant.MemberRole;
import com.challenge.chat.domain.member.constant.SocialType;
import com.challenge.chat.domain.member.entity.Member;
import com.challenge.chat.domain.member.service.MemberService;
import com.challenge.chat.exception.RestApiException;

@ExtendWith(MockitoExtension.class)
class ChatServiceTest {
	@Mock
	private ChatRepository chatRepository;
	@Mock
	private ChatRoomRepository chatRoomRepository;
	@Mock
	private MemberChatRoomRepository memberChatRoomRepository;

	@InjectMocks
	private ChatService chatService;
	@Mock
	private MemberService memberService;

	@Test
	@DisplayName("채팅방 조회 성공")
	void showRoomList() {
		//given
		List<ChatRoom> chatRooms = new ArrayList<>();
		ChatRoom room1 = ChatRoom.of("room UUID1", "room name1");
		ChatRoom room2 = ChatRoom.of("room UUID2", "room name2");
		ChatRoom room3 = ChatRoom.of("room UUID3", "room name3");
		chatRooms.add(room1);
		chatRooms.add(room2);
		chatRooms.add(room3);
		given(chatRoomRepository.findAll()).willReturn(chatRooms);

		//when
		List<ChatRoomDto> chatRoomDtoList = chatService.showRoomList();

		//then
		for (int i = 0; i < chatRoomDtoList.size(); i++) {
			assertThat(chatRoomDtoList.get(i).getId()).isEqualTo(chatRooms.get(i).getId());
			assertThat(chatRoomDtoList.get(i).getRoomId()).isEqualTo(chatRooms.get(i).getRoomId());
			assertThat(chatRoomDtoList.get(i).getRoomName()).isEqualTo(chatRooms.get(i).getRoomName());
		}
	}

	@Test
	@DisplayName("채팅방 생성 및 저장 성공")
	void createChatRoom() {
		//given
		ChatRoomDto chatRoomDto = new ChatRoomDto(1L, "room UUID1", "room name1");

		//when
		String result = chatService.createChatRoom(chatRoomDto);

		//then
		assertThat(result).isEqualTo("Successfully created chat room");
		// assertThat(result.getData()).isNotBlank();
	}

	@Test
	@DisplayName("채팅방 입장 성공: 이미 채팅방이 존재")
	void enterChatRoom() {
		//given
		Long memberId = 1L;
		ChatDto chatDto = setChatDto();
		Member member = setMember(memberId);
		ChatRoom chatRoom = ChatRoom.of("room UUID1", "room name1");

		//given Accessor 만들기
		Map<String, Object> attributes = new HashMap<>();
		SimpMessageHeaderAccessor accessor = SimpMessageHeaderAccessor.create();
		accessor.setSessionAttributes(attributes);

		//given @Mock Stubbing
		given(chatRoomRepository.findByRoomId(chatDto.getRoomId())).willReturn(Optional.of(chatRoom));
		given(memberService.findMemberByEmail(chatDto.getUserId())).willReturn(member);
		given(memberChatRoomRepository.findByMemberAndRoom(member, chatRoom)).willReturn(Optional.of(new MemberChatRoom(chatRoom, member)));

		//when
		ChatDto resultChatDto = chatService.enterChatRoom(chatDto, accessor);

		//then
		assertThat(chatDto).isEqualTo(resultChatDto);
		assertThat(chatDto.getMessage()).isEqualTo(chatDto.getSender() + "님 입장!! ο(=•ω＜=)ρ⌒☆");
	}

	@Test
	@DisplayName("채팅방 입장 성공: 채팅방 새로 생성")
	void enterChatRoom2() {
		//given
		ChatDto chatDto = setChatDto();
		ChatRoom chatRoom = ChatRoom.of("room UUID1", "room name1");
		Member member = setMember();

		//given Accessor 만들기
		Map<String, Object> attributes = new HashMap<>();
		SimpMessageHeaderAccessor accessor = SimpMessageHeaderAccessor.create();
		accessor.setSessionAttributes(attributes);

		//given @Mock Stubbing
		given(chatRoomRepository.findByRoomId(chatDto.getRoomId())).willReturn(Optional.of(chatRoom));
		given(memberService.findMemberByEmail(chatDto.getUserId())).willReturn(member);
		given(memberChatRoomRepository.findByMemberAndRoom(member, chatRoom)).willReturn(Optional.empty());

		//when
		ChatDto resultChatDto = chatService.enterChatRoom(chatDto, accessor);

		//then
		assertThat(chatDto).isEqualTo(resultChatDto);
		assertThat(chatDto.getMessage()).isEqualTo(chatDto.getSender() + "님 입장!! ο(=•ω＜=)ρ⌒☆");
	}
	@Test
	@DisplayName("채팅방 입장 실패: chatService 호출 실패")
	void enterChatRoomFail1() {
		//given
		ChatDto chatDto = setChatDto();

		//given Accessor 만들기
		Map<String, Object> attributes = new HashMap<>();
		SimpMessageHeaderAccessor accessor = SimpMessageHeaderAccessor.create();
		accessor.setSessionAttributes(attributes);

		//given @Mock Stubbing
		given(chatRoomRepository.findByRoomId(chatDto.getRoomId())).willReturn(Optional.empty());

		//when, then
		assertThatThrownBy(() -> chatService.enterChatRoom(chatDto, accessor))
			.isInstanceOf(RestApiException.class);
	}
	@Test
	@DisplayName("채팅방 입장 실패: memberService 호출 실패")
	void enterChatRoomFail2() {
		//given
		ChatDto chatDto = setChatDto();
		ChatRoom chatRoom = ChatRoom.of("room UUID1", "room name1");

		//given Accessor 만들기
		Map<String, Object> attributes = new HashMap<>();
		SimpMessageHeaderAccessor accessor = SimpMessageHeaderAccessor.create();
		accessor.setSessionAttributes(attributes);

		//given @Mock Stubbing
		given(chatRoomRepository.findByRoomId(chatDto.getRoomId())).willReturn(Optional.of(chatRoom));
		given(memberService.findMemberByEmail(chatDto.getUserId())).willThrow(NoSuchElementException.class);

		//when, then
		assertThatThrownBy(() -> chatService.enterChatRoom(chatDto, accessor))
			.isInstanceOf(NoSuchElementException.class);
	}

	@Test
	@DisplayName("채팅방 나가기 성공")
	void disconnectChatRoom() {
		//given
		String roomId = "roomId";
		String nickName = "nickName";
		String userId = "userId";

		//given Accessor 만들기
		Map<String, Object> attributes = new HashMap<>();
		SimpMessageHeaderAccessor accessor = SimpMessageHeaderAccessor.create();
		accessor.setSessionAttributes(attributes);
		Objects.requireNonNull(
			accessor.getSessionAttributes()).put("roomId", roomId);
		accessor.getSessionAttributes().put("nickName", nickName);
		accessor.getSessionAttributes().put("userId", userId);

		//when
		ChatDto resultChatDto = chatService.leaveChatRoom(accessor);

		//then
		assertThat(resultChatDto.getRoomId()).isEqualTo(roomId);
		assertThat(resultChatDto.getSender()).isEqualTo(nickName);
		assertThat(resultChatDto.getUserId()).isEqualTo(userId);
		assertThat(resultChatDto.getMessage()).isEqualTo(nickName + "님 퇴장!! ヽ(*。>Д<)o゜");
	}

	@Test
	@DisplayName("채팅방 메세지 조회 성공")
	void viewChat() {
		//given
		Member member = setMember();
		ChatRoom chatRoom = ChatRoom.of("room UUID1", "room name1");
		List<Chat> chatList = new ArrayList<>();
		List<ChatDto> chatDtoList = new ArrayList<>();

		//given @Mock Stubbing
		given(chatRoomRepository.findByRoomId(chatRoom.getRoomId())).willReturn(Optional.of(chatRoom));
		given(memberService.findMemberByEmail(member.getEmail())).willReturn(member);
		given(chatRepository.findAllByRoomIdOrderByCreatedAtAsc(chatRoom.getId())).willReturn(chatList);


		//when
		EnterUserDto resultUserDto = chatService.viewChat(chatRoom.getRoomId(), member.getEmail());

		//then
		assertThat(resultUserDto.getSender()).isEqualTo(member.getNickname());
		assertThat(resultUserDto.getUserId()).isEqualTo(member.getEmail());
		assertThat(resultUserDto.getRoomId()).isEqualTo(chatRoom.getRoomId());
		assertThat(resultUserDto.getChatList()).isEqualTo(chatDtoList);
	}

	@Test
	@DisplayName("채팅방 메세지 조회 실패: 채팅방 조회 실패")
	void viewChatFail1() {
		//given
		Member member = setMember();
		ChatRoom chatRoom = ChatRoom.of("room UUID1", "room name1");

		//given @Mock Stubbing
		given(chatRoomRepository.findByRoomId(chatRoom.getRoomId())).willReturn(Optional.empty());

		//when, then
		assertThatThrownBy(() -> chatService.viewChat(chatRoom.getRoomId(), member.getEmail()))
			.isInstanceOf(RestApiException.class);
	}
	@Test
	@DisplayName("채팅방 메세지 조회 실패: memberService 호출 실패")
	void viewChatFail2() {
		//given
		Member member = setMember();
		ChatRoom chatRoom = ChatRoom.of("room UUID1", "room name1");

		//given @Mock Stubbing
		given(chatRoomRepository.findByRoomId(chatRoom.getRoomId())).willReturn(Optional.of(chatRoom));
		given(memberService.findMemberByEmail(member.getEmail())).willThrow(NoSuchElementException.class);

		//when, then
		assertThatThrownBy(() -> chatService.viewChat(chatRoom.getRoomId(), member.getEmail()))
			.isInstanceOf(NoSuchElementException.class);
	}
	@Test
	@DisplayName("채팅 저장하기 성공")
	void sendChatRoom() {
		//given
		Member member = setMember();
		ChatDto chatDto = setChatDto();
		ChatRoom chatRoom = ChatRoom.of("room UUID1", "room name1");
		Chat chat = new Chat(chatDto, chatRoom, member, MessageType.TALK);

		//given @Mock Stubbing
		given(chatRoomRepository.findByRoomId(chatDto.getRoomId())).willReturn(Optional.of(chatRoom));
		given(memberService.findMemberByEmail(chatDto.getUserId())).willReturn(member);
		given(chatRepository.save(any())).willReturn(null);

		//when, then
		chatService.sendChatRoom(chatDto);
		// verify =
		verify(chatRepository, times(1)).save(refEq(chat));
	}

	@Test
	@DisplayName("채팅 저장하기 실패: 채팅방 조회 실패")
	void sendChatRoomFail1() {
		//given
		ChatDto chatDto = setChatDto();

		//given @Mock Stubbing
		given(chatRoomRepository.findByRoomId(chatDto.getRoomId())).willReturn(Optional.empty());

		//when, then
		assertThatThrownBy(() -> chatService.sendChatRoom(chatDto))
			.isInstanceOf(RestApiException.class);
	}
	@Test
	@DisplayName("채팅 저장하기 실패: memberService 호출 실패")
	void sendChatRoomFail2() {
		//given
		ChatDto chatDto = setChatDto();
		ChatRoom chatRoom = ChatRoom.of("room UUID1", "room name1");

		//given @Mock Stubbing
		given(chatRoomRepository.findByRoomId(chatDto.getRoomId())).willReturn(Optional.of(chatRoom));
		given(memberService.findMemberByEmail(chatDto.getUserId())).willThrow(NoSuchElementException.class);

		//when, then
		assertThatThrownBy(() -> chatService.sendChatRoom(chatDto))
			.isInstanceOf(NoSuchElementException.class);
	}


	@Test
	@DisplayName("roomId로 채팅방 가져오기 성공")
	void getRoomByRoomId() {
		//given
		ChatRoom chatRoom = ChatRoom.of("room UUID1", "room name1");

		//given @Mock Stubbing
		given(chatRoomRepository.findByRoomId(any())).willReturn(Optional.of(chatRoom));

		//when
		chatService.getRoomByRoomId(chatRoom.getRoomId());

		//then
		verify(chatRoomRepository).findByRoomId(chatRoom.getRoomId());

	}

	@Test
	@DisplayName("roomId로 채팅방 가져오기 실패: chatService 호출 실패")
	void getRoomByRoomIdFail() {
		//given
		String roomId = "userId";

		//given @Mock Stubbing
		given(chatRoomRepository.findByRoomId(roomId)).willReturn(Optional.empty());

		//then, when
		assertThatThrownBy(() -> chatService.getRoomByRoomId(roomId))
			.isInstanceOf(RestApiException.class);
	}

	private Member setMember() {
		List<MemberChatRoom> roomList = new ArrayList<>();
		return new Member(
			1L, "email", "password", "nickname",
			null, MemberRole.USER, SocialType.GOOGLE,
			"socialId", "refreshToken", roomList);
	}
	private Member setMember(Long id) {
		List<MemberChatRoom> roomList = new ArrayList<>();
		return new Member(id, "email", "password", "nickname",
			null, MemberRole.USER, SocialType.GOOGLE,
			"socialId", "refreshToken", roomList);
	}

	private ChatDto setChatDto() {
		return new ChatDto(MessageType.ENTER, "sender", "userId", "roomId", "date", "message");
	}
}