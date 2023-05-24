package com.challenge.chat.domain.chat.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;

import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.challenge.chat.domain.chat.dto.ChatDto;
import com.challenge.chat.domain.chat.dto.ChatRoomDto;
import com.challenge.chat.domain.chat.dto.EnterUserDto;
import com.challenge.chat.domain.chat.entity.Chat;
import com.challenge.chat.domain.chat.entity.ChatRoom;
import com.challenge.chat.domain.chat.entity.MessageType;
import com.challenge.chat.domain.chat.repository.ChatRepository;
import com.challenge.chat.domain.chat.repository.ChatRoomRepository;
import com.challenge.chat.domain.member.entity.Member;
import com.challenge.chat.domain.member.repository.MemberRepository;
import com.challenge.chat.global.dto.ResponseDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ChatService {
	private final ChatRoomRepository chatRoomRepository;
	private final MemberRepository memberRepository;
	private final ChatRepository chatRepository;

	public ResponseDto createChatRoom(String roomName, String host,
		User user) {
		//이미 reciever와 sender로 생성된 채팅방이 있는지 확인
		Optional<ChatRoom> findChatRoom = validExistChatRoom(host, roomName);
		//있으면 ChatRoom의 roomId 반환
		if (findChatRoom.isPresent())
			return ResponseDto.setSuccess("already has room and find Chatting Room Success!",
				findChatRoom.get().getRoomId());

		//없으면 receiver와 sender의 방을 생성해주고 roomId 반환
		//ChatRoom newChatRoom = ChatRoom.of(receiver, sender);
		//String roomId, String roomName, String host, String guest
		ChatRoom newChatRoom = new ChatRoom(roomName, host, user.getUsername());
		chatRoomRepository.save(newChatRoom);
		return ResponseDto.setSuccess("create ChatRoom success", newChatRoom.getRoomId());
	}

	public ChatDto enterChatRoom(ChatDto chatDto, SimpMessageHeaderAccessor headerAccessor) {

		//        Date date = new Date();
		//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//        String dateformat = format.format(date);
		//
		//        chatDto.setDate(dateformat);

		// 채팅방 찾기
		ChatRoom chatRoom = validExistChatRoom(chatDto.getRoomId());
		// 예외처리
		//반환 결과를 socket session에 사용자의 id로 저장
		Objects.requireNonNull(headerAccessor.getSessionAttributes()).put("userId", chatDto.getUserId());
		headerAccessor.getSessionAttributes().put("roomId", chatDto.getRoomId());
		headerAccessor.getSessionAttributes().put("nickName", chatDto.getSender());

		Member member = userIDCheck(chatDto.getUserId());
		ChatRoom room = roomIdCheck(chatDto.getRoomId());
		member.enterRoom(room);

		chatDto.setMessage(chatDto.getSender() + "님 입장!! ο(=•ω＜=)ρ⌒☆");

		Long headCount = memberRepository.countAllByRoomId(chatRoom.getId());
		chatRoom.updateCount(headCount);
		return chatDto;
	}

	public ChatDto disconnectChatRoom(SimpMessageHeaderAccessor headerAccessor) {
		String roomId = (String)headerAccessor.getSessionAttributes().get("roomId");
		String nickName = (String)headerAccessor.getSessionAttributes().get("nickName");
		String userId = (String)headerAccessor.getSessionAttributes().get("userId");
		Member member = userNameCheck(nickName);
		ChatRoom room = roomIdCheck(roomId);
		member.exitRoom(room);

		ChatDto chatDto = ChatDto.builder()
			.type(MessageType.LEAVE)
			.roomId(roomId)
			.sender(nickName)
			.userId(userId)
			.message(nickName + "님 퇴장!! ヽ(*。>Д<)o゜")
			.build();

		Long headCount = memberRepository.countAllByRoomId(room.getId());
		room.updateCount(headCount);
		if (headCount == 0) {
			chatRoomRepository.deleteByRoomId(roomId);
		}

		return chatDto;
	}

	public Optional<ChatRoom> validExistChatRoom(String roomName) {
		return chatRoomRepository.findByRoomName(roomName);
	}

	public ChatRoom validExistChatRoom(String roomId) {
		return chatRoomRepository.findByRoomId(roomId).orElseThrow(
			() -> new NoSuchElementException("채팅방이 존재하지 않습니다.")
		);
	}

	public List<ChatRoomDto> showRoomList() {
		List<ChatRoom> chatRoomList = chatRoomRepository.findAll();
		List<ChatRoomDto> chatRoomDtoList = new ArrayList<>();
		for (ChatRoom chatRoom : chatRoomList) {
			ChatRoomDto chatRoomDto = new ChatRoomDto(chatRoom);
			chatRoomDtoList.add(chatRoomDto);
		}
		return chatRoomDtoList;
	}

	public ChatRoom roomIdCheck(String roomId) {
		return chatRoomRepository.findByRoomId(roomId).orElseThrow(
			() -> new IllegalArgumentException("존재하지 않는 채팅방입니다.")
		);
	}

	public Member userNameCheck(String userName) {
		return memberRepository.findByNickname(userName).orElseThrow(
			() -> new IllegalArgumentException("존재하지 않는 유저입니다.")
		);
	}

	public Member userIDCheck(String email) {
		return memberRepository.findByEmail(email).orElseThrow(
			() -> new IllegalArgumentException("존재하지 않는 유저입니다.")
		);
	}

	public EnterUserDto findRoom(String roomId, String email) {
		ChatRoom chatRoom = roomIdCheck(roomId);
		Member member = userIDCheck(email);
		List<Chat> chatList = chatRepository.findAllByRoom_IdOrderByCreatedAtAsc(chatRoom.getId());
		List<ChatDto> chatDtoList = new ArrayList<>();
		for (Chat chat : chatList) {
			ChatDto chatDto = new ChatDto(chat);
			chatDtoList.add(chatDto);
		}
		return new EnterUserDto(member.getNickname(), member.getEmail(), chatRoom.getRoomId(), chatDtoList);
	}

	public void sendChatRoom(ChatDto chatDto, SimpMessageHeaderAccessor headerAccessor) {

		ChatRoom room = roomIdCheck(chatDto.getRoomId());
		Member member = userIDCheck(chatDto.getUserId());
		MessageType type = MessageType.TALK;

		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateformat = format.format(date);
		chatDto.setDate(dateformat);

		Chat chat = new Chat(chatDto, room, member, type);
		chatRepository.save(chat);
	}
}