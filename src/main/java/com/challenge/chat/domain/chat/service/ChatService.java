package com.challenge.chat.domain.chat.service;

import com.challenge.chat.domain.chat.dto.ChatDto;
import com.challenge.chat.domain.chat.dto.ChatRoomDto;
import com.challenge.chat.domain.chat.dto.response.ChatSearchResponse;
import com.challenge.chat.domain.chat.entity.Chat;
// import com.challenge.chat.domain.chat.entity.ChatES;
import com.challenge.chat.domain.chat.entity.ChatRoom;
import com.challenge.chat.domain.chat.entity.MemberChatRoom;
import com.challenge.chat.domain.chat.entity.MessageType;
import com.challenge.chat.domain.chat.repository.ChatRepository;
import com.challenge.chat.domain.chat.repository.ChatRoomRepository;
// import com.challenge.chat.domain.chat.repository.ChatSearchRepository;
// import com.challenge.chat.domain.chat.repository.ChatSearchRepository;
import com.challenge.chat.domain.chat.repository.MemberChatRoomRepository;
import com.challenge.chat.exception.RestApiException;
import com.challenge.chat.exception.dto.ChatErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.cassandra.core.CassandraTemplate;

import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChatService {

	private final MemberChatRoomRepository memberChatRoomRepository;
	private final ChatRoomRepository chatRoomRepository;
	private final ChatRepository chatRepository;
	private final CassandraTemplate cassandraTemplate;

	// private final ChatSearchRepository chatSearchRepository;
	// private final ElasticsearchOperations elasticsearchOperations;

	@Transactional
	public ChatRoomDto makeChatRoom(final ChatRoomDto chatRoomDto, final String memberEmail) {
		log.info("Service : 채팅방 생성");

		ChatRoom chatRoom = ChatRoomDto.toEntity(chatRoomDto);

		// TODO : 비동기적으로 chatRoom 과 memberChatRoom을 저장하기
		chatRoomRepository.save(chatRoom);
		memberChatRoomRepository.save(new MemberChatRoom(chatRoom.getRoomId(), memberEmail));

		return ChatRoomDto.from(chatRoom);
	}

	@Transactional
	public ChatRoomDto registerChatRoom(final ChatRoomDto chatRoomDto, final String memberEmail) {
		log.info("Service : 채팅방 추가");

		ChatRoom chatRoom = findChatRoom(chatRoomDto.getRoomId());
		memberChatRoomRepository.save(new MemberChatRoom(chatRoomDto.getRoomId(), memberEmail));

		return ChatRoomDto.from(chatRoom);
	}

	@Transactional(readOnly = true)
	public List<ChatRoomDto> searchChatRoomList(final String memberEmail) {
		log.info("Service : 채팅방 리스트 조회");

		// TODO : 채팅방 리스트를 가져오는 동작이 2번의 쿼리를 동기적으로 실행해서 오히려 느려질 수 있는 지점이 될 수 있음
		List<String> roomIds = findChatRoomId(memberEmail);

		log.info("Service : 채팅방 목록 가져오기 성공!");

		return chatRoomRepository.findByRoomIdIn(roomIds)
			.stream()
			.map(ChatRoomDto::from)
			.collect(Collectors.toList());
	}

	@Transactional(readOnly = true)
	public List<ChatDto> searchChatList(final String roomId, final String memberEmail) {
		log.info("Service : 채팅방 메세지 조회");

		MemberChatRoom memberChatRoom = findMemberChatRoom(roomId, memberEmail);

		return chatRepository
			.findByRoomId(roomId)
			.stream()
			.sorted(Comparator.comparing(Chat::getCreatedAt))
			.map(ChatDto::from)
			.collect(Collectors.toList());
	}

	public ChatDto makeEnterMessageAndSetSessionAttribute(ChatDto chatDto, SimpMessageHeaderAccessor headerAccessor) {
		log.info("Service : 채팅방 입장");

		// socket session에 사용자의 정보 저장
		try {
			Objects.requireNonNull(headerAccessor.getSessionAttributes()).put("email", chatDto.getEmail());
			headerAccessor.getSessionAttributes().put("roomId", chatDto.getRoomId());
			headerAccessor.getSessionAttributes().put("nickname", chatDto.getNickname());
		} catch (Exception e) {
			throw new RestApiException(ChatErrorCode.SOCKET_CONNECTION_ERROR);
		}

		chatDto.setMessage(chatDto.getNickname() + "님 입장!! ο(=•ω＜=)ρ⌒☆");

		return chatDto;
	}

	@Transactional
	public void sendChatRoom(ChatDto chatDto) {
		log.info("Service : 채팅 보내기 - {}", chatDto.getMessage());

		// MongoDB 저장
		chatRepository.save(ChatDto.toEntity(chatDto));
		// ElasticSearch 저장
		// chatSearchRepository.save(ChatDto.toElasticEntity(chatDto));
	}

	// public List<ChatSearchResponse> findChatList(final String roomId, final String message, final Pageable pageable) {
	// 	log.info("Service: 채팅 검색하기 - message: {}, roomId: {}", message, roomId);
	//
	// 	QueryBuilder queryBuilder = QueryBuilders.boolQuery()
	// 		.must(QueryBuilders.matchQuery("message", message).analyzer("korean"))
	// 		.must(QueryBuilders.matchQuery("roomId", roomId));
	//
	// 	NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
	// 		.withQuery(queryBuilder)
	// 		.withPageable(pageable)
	// 		.build();
	//
	// 	SearchHits<ChatES> searchHits = elasticsearchOperations.search(searchQuery, ChatES.class);
	//
	// 	return searchHits.stream()
	// 		.map(SearchHit::getContent)
	// 		.map(ChatSearchResponse::from)
	// 		.collect(Collectors.toList());
	// }

	public ChatDto leaveChatRoom(SimpMessageHeaderAccessor headerAccessor) {
		log.info("Service 채팅방 나가기");

		String roomId = (String)headerAccessor.getSessionAttributes().get("roomId");
		String nickName = (String)headerAccessor.getSessionAttributes().get("nickName");
		String userId = (String)headerAccessor.getSessionAttributes().get("userId");

		return ChatDto.builder()
			.type(MessageType.LEAVE)
			.roomId(roomId)
			.nickname(nickName)
			.email(userId)
			.message(nickName + "님 퇴장!! ヽ(*。>Д<)o゜")
			.build();
	}

	public ChatRoom findChatRoom(String roomId) {
		return chatRoomRepository.findByRoomId(roomId).orElseThrow(
			() -> new RestApiException(ChatErrorCode.CHATROOM_NOT_FOUND));
	}

	public MemberChatRoom findMemberChatRoom(final String roomId, final String memberEmail) {
		return memberChatRoomRepository.findByMemberEmailAndRoomId(memberEmail, roomId).orElseThrow(
			() -> new RestApiException(ChatErrorCode.CHATROOM_NOT_FOUND)
		);
	}

	public List<String> findChatRoomId(final String email) {
		List<MemberChatRoom> memberChatRoomList = memberChatRoomRepository.findByMemberEmail(email).orElse(null);
		if (memberChatRoomList == null) {
			return null;
		}
		return memberChatRoomList.stream()
			.map(MemberChatRoom::getRoomId)
			.collect(Collectors.toList());
	}
}