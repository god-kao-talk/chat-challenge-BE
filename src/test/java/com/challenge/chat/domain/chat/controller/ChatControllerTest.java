package com.challenge.chat.domain.chat.controller;

import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.challenge.chat.domain.chat.dto.ChatRoomDto;
import com.challenge.chat.domain.chat.service.ChatService;
import com.challenge.chat.global.dto.ResponseDto;
import com.nimbusds.jose.shaded.gson.Gson;

@WebMvcTest(ChatController.class)
@MockBean(JpaMetamodelMappingContext.class)
class ChatControllerTest {

	// @LocalServerPort
	// private Integer port;
	//
	// private WebSocketStompClient webSocketStompClient;

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private ChatService chatService;

	@MockBean
	private SimpMessagingTemplate msgOperation;

	@BeforeEach
	void setup() {

	}

	@Test
	@WithMockUser
	void showRoomList() throws Exception {
		//given
		given(chatService.showRoomList()).willReturn(new ArrayList<ChatRoomDto>());

		//when, then
		mockMvc.perform(
				get("/room"))
			.andExpect(status().isOk());
	}

	@Test
	@WithMockUser
	void createChatRoom() throws Exception {
		//given
		// ChatRoomDto chatRoomDto = new ChatRoomDto();
		// chatRoomDto.setId(123L);
		// chatRoomDto.setRoomId("acNiznce123ds");
		// chatRoomDto.setRoomName("TestRoom");
		// given(chatService.createChatRoom(chatRoomDto)).willReturn(
		// 	ResponseDto.setSuccess("create ChatRoom success", chatRoomDto.getRoomId()));
		// Gson gson = new Gson();
		// String content = gson.toJson(chatRoomDto);
		//
		// //when, then
		// mockMvc.perform(
		// 		post("/chat")
		// 			.content(content)
		// 			.contentType(MediaType.APPLICATION_JSON))
		// 	.andExpect(status().isOk());
	}
	//showRoomList()와 다른점이 뭐지? post라서 안된다?

	// @Test
	// @WithMockUser(username = "user")
	// 	//username = “user”, password = “password”, role = “USER”
	// void viewChat(@AuthenticationPrincipal User user) throws Exception {
	// 	given(chatService.viewChat("roomId", user.getUsername()))
	// 		.willReturn(new EnterUserDto("user", "user", "roomId", null));
	//
	// 	String roomId = "roomId";
	//
	// 	mockMvc.perform(
	// 			get("/chat/{" + roomId + "}")
	// 				.with(SecurityMockMvcRequestPostProcessors.user("user")))
	// 		.andExpect(status().isOk());
	// }

	@Test
	void enterChatRoom() {
	}

	@Test
	void sendChatRoom() {
	}

	@Test
	void webSocketDisconnectListener() {
	}
}