package com.challenge.chat.domain.chat.controller;

import com.challenge.chat.domain.chat.constant.KafkaConstants;
import com.challenge.chat.domain.chat.dto.ChatDto;
import com.challenge.chat.domain.chat.dto.ChatRoomDto;
import com.challenge.chat.domain.chat.dto.request.ChatRoomAddRequest;
import com.challenge.chat.domain.chat.dto.request.ChatRoomCreateRequest;
import com.challenge.chat.domain.chat.entity.Chat;
import com.challenge.chat.domain.chat.service.ChatService;
import com.challenge.chat.domain.chat.service.Producer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

@RestController
@Slf4j
@CrossOrigin
@RequiredArgsConstructor
public class ChatController {

	private final ChatService chatService;
	private final Producer producer;
	private final SimpMessagingTemplate msgOperation;

	private final RabbitTemplate rabbitTemplate;

	private final static String CHAT_EXCHANGE_NAME = "chat.exchange";

	@PostMapping("/chat")
	public ResponseEntity<ChatRoomDto> createChatRoom(
		@RequestBody final ChatRoomCreateRequest request,
		@AuthenticationPrincipal final User user) {

		return ResponseEntity.status(HttpStatus.OK)
			.body(chatService.makeChatRoom(request.getRoomName(), user.getUsername()));
	}

	@PostMapping("/chat/room")
	public ResponseEntity<ChatRoomDto> addChatRoom(
		@RequestBody final ChatRoomAddRequest request,
		@AuthenticationPrincipal final User user) {

		return ResponseEntity.status(HttpStatus.OK)
			.body(chatService.registerChatRoom(request.getRoomCode(), user.getUsername()));
	}

	@GetMapping("/chat/room")
	public ResponseEntity<List<ChatRoomDto>> showChatRoomList(
		@AuthenticationPrincipal final User user) {

		return ResponseEntity.status(HttpStatus.OK)
			.body(chatService.searchChatRoomList(user.getUsername()));
	}

	@GetMapping("/chat/{room-code}")
	public ResponseEntity<List<ChatDto>> showChatList(
		@PathVariable("room-code") final String roomCode,
		@AuthenticationPrincipal final User user) {

		return ResponseEntity.status(HttpStatus.OK)
			.body(chatService.searchChatList(roomCode, user.getUsername()));
	}

	@MessageMapping("chat.enter")
	public void enterChatRoom(
		@RequestBody ChatDto chatDto,
		SimpMessageHeaderAccessor headerAccessor) {

		ChatDto newChatDto = chatService.makeEnterMessageAndSetSessionAttribute(chatDto, headerAccessor);
		// producer.send(
		// 	KafkaConstants.KAFKA_TOPIC,
		// 	newchatDto
		// );

		// msgOperation.convertAndSend("/topic/chat/room/" + chatDto.getRoomCode(), newchatDto);
		rabbitTemplate.convertAndSend(CHAT_EXCHANGE_NAME, "room." + newChatDto.getRoomCode(), newChatDto);
	}

	@MessageMapping("chat.send")
	public void sendChatRoom(
		@RequestBody ChatDto chatDto) {

		Chat chat = ChatDto.toEntity(chatDto);
		chat.setCreatedAt();

		chatDto.setCreatedAt(Instant.ofEpochMilli(chat.getCreatedAt()));
		log.info("현재 시간은 {}", Instant.ofEpochMilli(chat.getCreatedAt()));

		producer.send(
			KafkaConstants.KAFKA_TOPIC,
			chat
		);
		rabbitTemplate.convertAndSend(CHAT_EXCHANGE_NAME, "room." + chatDto.getRoomCode(), chatDto);
		// chatService.sendChatRoom(chatDto);
		// msgOperation.convertAndSend("/topic/chat/room/" + chatDto.getRoomCode(), chatDto);
	}

	// @EventListener
	// public void webSocketDisconnectListener(SessionDisconnectEvent event) {
	// 	StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
	// 	log.info("Controller webSocketDisconnectListener, 채팅방 나가기");
	// 	ChatDto chatDto = chatService.leaveChatRoom(headerAccessor);
	// 	msgOperation.convertAndSend("/topic/chat/room/" + chatDto.getRoomId(), chatDto);
	// }
}