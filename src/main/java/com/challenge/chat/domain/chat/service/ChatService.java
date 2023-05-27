package com.challenge.chat.domain.chat.service;

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
import com.challenge.chat.domain.member.entity.Member;
import com.challenge.chat.domain.member.repository.MemberRepository;
import com.challenge.chat.domain.member.service.MemberService;
import com.challenge.chat.global.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ChatService {

	private final MemberChatRoomRepository memberChatRoomRepository;
	private final ChatRoomRepository chatRoomRepository;
	private final MemberRepository memberRepository;
	private final ChatRepository chatRepository;
	private final MemberService memberService;

	// 채팅방 조회
	public List<ChatRoomDto> showRoomList() {
		log.info("Service 채팅방 조회");
		List<ChatRoom> chatRoomList = chatRoomRepository.findAll();
		List<ChatRoomDto> chatRoomDtoList = new ArrayList<>();
		for (ChatRoom chatRoom : chatRoomList) {
			ChatRoomDto chatRoomDto = new ChatRoomDto(chatRoom);
			chatRoomDtoList.add(chatRoomDto);
		}
		return chatRoomDtoList;
	}

	// 채팅방 생성
	public ResponseDto<String> createChatRoom(ChatRoomDto chatRoomDto) {
		log.info("Service 채팅방 생성");
		ChatRoom newChatRoom = new ChatRoom(chatRoomDto.getRoomName());
		chatRoomRepository.save(newChatRoom);
		return ResponseDto.setSuccess("create ChatRoom success", newChatRoom.getRoomId());
	}

	// 채팅방 입장
	public ChatDto enterChatRoom(ChatDto chatDto, SimpMessageHeaderAccessor headerAccessor) {
		log.info("Service 채팅방 입장");
		ChatRoom chatRoom = getRoomByRoomId(chatDto.getRoomId());
		Member member = memberService.findMemberByEmail(chatDto.getUserId());
		// 중간 테이블에 save
		// 중간 테이블에 이미 연결되어 있다면 새로 생성 안함
		Optional<MemberChatRoom> memberChatRoom = memberChatRoomRepository.findByMemberAndRoom(member, chatRoom);

		if (memberChatRoom.isEmpty()){
			memberChatRoomRepository.save(new MemberChatRoom(chatRoom, member));
		}

		//반환 결과를 socket session 에 사용자의 id로 저장
		Objects.requireNonNull(headerAccessor.getSessionAttributes()).put("userId", chatDto.getUserId());
		headerAccessor.getSessionAttributes().put("roomId", chatDto.getRoomId());
		headerAccessor.getSessionAttributes().put("nickName", chatDto.getSender());

		chatDto.setMessage(chatDto.getSender() + "님 입장!! ο(=•ω＜=)ρ⌒☆");
		return chatDto;
	}

	// 채팅방 나가기
	public ChatDto disconnectChatRoom(SimpMessageHeaderAccessor headerAccessor) {
		log.info("Service 채팅방 나가기");
		String roomId = (String)headerAccessor.getSessionAttributes().get("roomId");
		String nickName = (String)headerAccessor.getSessionAttributes().get("nickName");
		String userId = (String)headerAccessor.getSessionAttributes().get("userId");

		ChatDto chatDto = ChatDto.builder()
			.type(MessageType.LEAVE)
			.roomId(roomId)
			.sender(nickName)
			.userId(userId)
			.message(nickName + "님 퇴장!! ヽ(*。>Д<)o゜")
			.build();

		return chatDto;
	}

	// 채팅 메세지 조회
	public EnterUserDto viewChat(String roomId, String email) {
		log.info("Service 채팅방 메세지 조회");
		ChatRoom chatRoom = getRoomByRoomId(roomId);
		Member member = memberService.findMemberByEmail(email);
		List<Chat> chatList = chatRepository.findAllByRoomIdOrderByCreatedAtAsc(chatRoom.getId());
		List<ChatDto> chatDtoList = new ArrayList<>();
		for (Chat chat : chatList) {
			ChatDto chatDto = new ChatDto(chat);
			chatDtoList.add(chatDto);
		}
		return new EnterUserDto(member.getNickname(), member.getEmail(), chatRoom.getRoomId(), chatDtoList);
	}

	// 채팅 보내기
	public void sendChatRoom(ChatDto chatDto) {
		log.info("Service 채팅 SEND");

		ChatRoom room = getRoomByRoomId(chatDto.getRoomId());
		Member member = memberService.findMemberByEmail(chatDto.getUserId());
		MessageType type = MessageType.TALK;

		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateformat = format.format(date);
		chatDto.setDate(dateformat);

		Chat chat = new Chat(chatDto, room, member, type);
		chatRepository.save(chat);
	}

	public ChatRoom getRoomByRoomId(String roomId) {
		return chatRoomRepository.findByRoomId(roomId).orElseThrow(
			() -> new IllegalArgumentException("존재하지 않는 채팅방입니다.")
		);
	}
}