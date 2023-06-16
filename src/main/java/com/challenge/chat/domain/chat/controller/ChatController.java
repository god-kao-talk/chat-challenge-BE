package com.challenge.chat.domain.chat.controller;

import com.challenge.chat.domain.chat.dto.ChatDto;
import com.challenge.chat.domain.chat.dto.ChatRoomDto;
import com.challenge.chat.domain.chat.dto.request.ChatRoomAddRequest;
import com.challenge.chat.domain.chat.dto.request.ChatRoomCreateRequest;
import com.challenge.chat.domain.chat.dto.response.ChatSearchResponse;
import com.challenge.chat.domain.chat.service.ChatService;
import com.challenge.chat.domain.chat.service.Producer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@CrossOrigin
@RequiredArgsConstructor
public class ChatController {

	private final ChatService chatService;
	private final Producer producer;
	private final SimpMessagingTemplate msgOperation;

	@PostMapping("/chat")
	public ResponseEntity<ChatRoomDto> createChatRoom(
		@RequestBody final ChatRoomCreateRequest request,
		@AuthenticationPrincipal final User user) {

		log.info("Controller : 채팅방 생성, User의 email은 {} 입니다", user.getUsername());

		return ResponseEntity.status(HttpStatus.OK)
			.body(chatService.makeChatRoom(ChatRoomDto.from(request), user.getUsername()));
	}

	@PostMapping("/chat/room")
	public ResponseEntity<ChatRoomDto> addChatRoom(
		@RequestBody final ChatRoomAddRequest request,
		@AuthenticationPrincipal final User user) {

		log.info("Controller : 채팅방 추가, User의 email은 {} 입니다", user.getUsername());

		return ResponseEntity.status(HttpStatus.OK)
			.body(chatService.registerChatRoom(ChatRoomDto.from(request), user.getUsername()));
	}

	@GetMapping("/chat/room")
	public ResponseEntity<List<ChatRoomDto>> showChatRoomList(
		@AuthenticationPrincipal final User user) {

		log.info("Controller : 채팅방 조회, User의 email은 {} 입니다", user.getUsername());

		return ResponseEntity.status(HttpStatus.OK)
			.body(chatService.searchChatRoomList(user.getUsername()));
	}

	@GetMapping("/chat/{room-id}")
	public ResponseEntity<List<ChatDto>> showChatList(
		@PathVariable("room-id") final String roomId,
		@AuthenticationPrincipal final User user) {

		log.info("Controller : 채팅 내역 조회, User의 email은 {} 입니다", user.getUsername());

		return ResponseEntity.status(HttpStatus.OK)
			.body(chatService.searchChatList(roomId, user.getUsername()));
	}

	@MessageMapping("/chat/enter")
	public void enterChatRoom(
		@RequestBody ChatDto chatDto,
		SimpMessageHeaderAccessor headerAccessor) {

		log.info("Controller : 채팅방 입장");
		ChatDto newchatDto = chatService.makeEnterMessageAndSetSessionAttribute(chatDto, headerAccessor);
		// producer.send(
		// 	KafkaConstants.KAFKA_TOPIC, newchatDto
		// 	);

		msgOperation.convertAndSend("/topic/chat/room/" + chatDto.getRoomId(), newchatDto);
	}

	@MessageMapping("/chat/send")
	public void sendChatRoom(
		@RequestBody ChatDto chatDto) {

		log.info("Controller : 채팅 보내기 - {}", chatDto.getMessage());

		// producer.send(
		// 	KafkaConstants.KAFKA_TOPIC,
		// 	chatDto
		// );
		chatService.sendChatRoom(chatDto);
		msgOperation.convertAndSend("/topic/chat/room/" + chatDto.getRoomId(), chatDto);
	}

	// @GetMapping("/chat/{room-id}/{message}")
	// public ResponseEntity<List<ChatSearchResponse>> searchChatList(
	// 	@PathVariable("room-id") final String roomId,
	// 	@PathVariable("message") final String message,
	// 	final Pageable pageable) {
	//
	// 	log.info("Controller : 채팅 메시지 검색");
	//
	// 	return ResponseEntity.status(HttpStatus.OK)
	// 		.body(chatService.findChatList(roomId, message, pageable));
	// }

	// @EventListener
	// public void webSocketDisconnectListener(SessionDisconnectEvent event) {
	// 	StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
	// 	log.info("Controller webSocketDisconnectListener, 채팅방 나가기");
	// 	ChatDto chatDto = chatService.leaveChatRoom(headerAccessor);
	// 	msgOperation.convertAndSend("/topic/chat/room/" + chatDto.getRoomId(), chatDto);
	// }
}