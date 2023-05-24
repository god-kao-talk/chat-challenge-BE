package com.challenge.chat.domain.chat.controller;

import java.util.List;

import org.springframework.context.event.EventListener;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import com.challenge.chat.domain.chat.dto.ChatDto;
import com.challenge.chat.domain.chat.dto.ChatRoomDto;
import com.challenge.chat.domain.chat.dto.EnterUserDto;
import com.challenge.chat.domain.chat.service.ChatService;
import com.challenge.chat.global.dto.ResponseDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequiredArgsConstructor
public class ChatController {

	private final ChatService chatService;
	private final SimpMessagingTemplate msgOperation;

	@PostMapping("/chat")
	public ResponseDto createChatRoom(@RequestBody ChatRoomDto chatRoomDto,
		@AuthenticationPrincipal User user) {
		log.info("User의 email 입니다. {}", user.getUsername());
		return chatService.createChatRoom(chatRoomDto, user);
	}

	@GetMapping("/chat/{roomId}")
	public EnterUserDto findChatRoom(@PathVariable String roomId,
		@AuthenticationPrincipal User user) {
		return chatService.findRoom(roomId, user.getUsername());
	}

	@MessageMapping("/chat/enter")
	@SendTo("/topic/chat/room")
	public void enterChatRoom(@RequestBody ChatDto chatDto, SimpMessageHeaderAccessor headerAccessor) throws Exception {
		ChatDto newchatdto = chatService.enterChatRoom(chatDto, headerAccessor);
		msgOperation.convertAndSend("/topic/chat/room" + chatDto.getRoomId(), newchatdto);
	}

	@MessageMapping("/chat/send")
	@SendTo("/topic/chat/room")
	public void sendChatRoom(ChatDto chatDto, SimpMessageHeaderAccessor headerAccessor) throws Exception {
		chatService.sendChatRoom(chatDto, headerAccessor);
		msgOperation.convertAndSend("/topic/chat/room" + chatDto.getRoomId(), chatDto);
	}

	@EventListener
	public void webSocketDisconnectListener(SessionDisconnectEvent event) {
		StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
		ChatDto chatDto = chatService.disconnectChatRoom(headerAccessor);
		msgOperation.convertAndSend("/topic/chat/room" + chatDto.getRoomId(), chatDto);
	}

	@GetMapping("/room")
	public List<ChatRoomDto> showRoomList() {
		return chatService.showRoomList();
	}
}