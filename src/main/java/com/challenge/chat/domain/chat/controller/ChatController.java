package com.challenge.chat.domain.chat.controller;

import com.challenge.chat.domain.chat.dto.ChatDto;
import com.challenge.chat.domain.chat.dto.ChatRoomDto;
import com.challenge.chat.domain.chat.dto.EnterUserDto;
import com.challenge.chat.domain.chat.service.ChatService;
import com.challenge.chat.global.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
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
@RequiredArgsConstructor
public class ChatController {

	private final ChatService chatService;
	private final SimpMessagingTemplate msgOperation;

	// 채팅방 조회
	@GetMapping("/room")
	public List<ChatRoomDto> showRoomList() {
		return chatService.showRoomList();
	}

	// version 1 단체 채팅방 생성
	@PostMapping("/chat")
	public ResponseDto<?> createChatRoom(@RequestBody ChatRoomDto chatRoomDto,
		@AuthenticationPrincipal User user) {
		log.info("User의 email 입니다. {}", user.getUsername());
		return chatService.createChatRoom(chatRoomDto);
	}

	// version 1 단체 채팅방 채팅 내역 조회
	@GetMapping("/chat/{roomId}")
	public EnterUserDto viewChat(@PathVariable String roomId,
									 @AuthenticationPrincipal User user) {
		return chatService.viewChat(roomId, user.getUsername());
	}

	// version 1 단체 채팅방 입장하기
	@MessageMapping("/chat/enter")
	@SendTo("/topic/chat/room")
	public void enterChatRoom(@RequestBody ChatDto chatDto, SimpMessageHeaderAccessor headerAccessor) throws Exception {
		ChatDto newchatdto = chatService.enterChatRoom(chatDto, headerAccessor);
		msgOperation.convertAndSend("/topic/chat/room" + chatDto.getRoomId(), newchatdto);
	}

	// version 1 단체 채팅방 채팅 Send
	@MessageMapping("/chat/send")
	@SendTo("/topic/chat/room")
	public void sendChatRoom(ChatDto chatDto) throws Exception {
		chatService.sendChatRoom(chatDto);
		msgOperation.convertAndSend("/topic/chat/room" + chatDto.getRoomId(), chatDto);
	}

	// version 1 채팅방 나가기
	@EventListener
	public void webSocketDisconnectListener(SessionDisconnectEvent event) {
		StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
		ChatDto chatDto = chatService.disconnectChatRoom(headerAccessor);
		msgOperation.convertAndSend("/topic/chat/room" + chatDto.getRoomId(), chatDto);
	}
}