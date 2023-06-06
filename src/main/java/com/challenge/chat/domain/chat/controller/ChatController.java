package com.challenge.chat.domain.chat.controller;

import com.challenge.chat.domain.chat.constant.KafkaConstants;
import com.challenge.chat.domain.chat.dto.ChatDto;
import com.challenge.chat.domain.chat.dto.ChatRoomDto;
import com.challenge.chat.domain.chat.dto.EnterUserDto;
import com.challenge.chat.domain.chat.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.List;

@RestController
@Slf4j
@CrossOrigin
@RequiredArgsConstructor
public class ChatController {

	private final ChatService chatService;
	private final SimpMessagingTemplate msgOperation;
	private final KafkaTemplate<String, ChatDto> kafkaTemplate;

	// 채팅방 조회
	@GetMapping("/room")
	public ResponseEntity<List<ChatRoomDto>> showRoomList() {
		log.info("Controller showRoomList, 채팅방 조회 호출");
		return ResponseEntity.status(HttpStatus.OK)
			.body(chatService.showRoomList());
	}

	// 채팅방 생성
	@PostMapping("/chat")
	public ResponseEntity<String> createChatRoom(@RequestBody ChatRoomDto chatRoomDto, @AuthenticationPrincipal User user) {
		log.info("Controller createChatRoom, 채팅방 생성 User의 email 입니다. {}", user.getUsername());
		return ResponseEntity.status(HttpStatus.OK)
			.body(chatService.createChatRoom(chatRoomDto));
	}

	// 채팅방 채팅 내역 조회
	@GetMapping("/chat/{roomId}")
	public ResponseEntity<EnterUserDto> viewChat(@PathVariable String roomId, @AuthenticationPrincipal User user) {
		log.info("Controller viewChat, 채팅 내역 조회");
		return ResponseEntity.status(HttpStatus.OK)
			.body(chatService.viewChat(roomId, user.getUsername()));
	}

	// 채팅방 입장하기
	@MessageMapping("/chat/enter")
	public void enterChatRoom(@RequestBody ChatDto chatDto, SimpMessageHeaderAccessor headerAccessor) throws Exception {
		log.info("Controller enterChatRoom, 채팅방 입장");
		msgOperation.convertAndSend(
				"/topic/chat/room/" + chatDto.getRoomId(),
				chatService.enterChatRoom(chatDto, headerAccessor));
		kafkaTemplate.send(KafkaConstants.KAFKA_TOPIC, chatDto).get();

	}

	// 채팅방 채팅 보내기
//	@MessageMapping("/chat/send")
	@PostMapping("/chat/send")
	public void sendChatRoom(@RequestBody ChatDto chatDto) throws Exception {
		log.info("Controller sendChatRoom, 채팅 보내기 {}", chatDto);
		chatService.sendChatRoom(chatDto);
		msgOperation.convertAndSend(
				"/topic/chat/room/" + chatDto.getRoomId(),
				chatDto);
		kafkaTemplate.send(KafkaConstants.KAFKA_TOPIC, chatDto).get();
	}

	// 채팅방 나가기
	@EventListener
	public void webSocketDisconnectListener(SessionDisconnectEvent event) {
		StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
		log.info("Controller webSocketDisconnectListener, 채팅방 나가기");
		ChatDto chatDto = chatService.leaveChatRoom(headerAccessor);
		msgOperation.convertAndSend("/topic/chat/room/" + chatDto.getRoomId(), chatDto);
	}
}