// package com.challenge.chat.domain.chat.entity;
//
// import java.time.Instant;
//
// import javax.persistence.Id;
//
// import org.springframework.data.elasticsearch.annotations.DateFormat;
// import org.springframework.data.elasticsearch.annotations.Document;
// import org.springframework.data.elasticsearch.annotations.Field;
// import org.springframework.data.elasticsearch.annotations.FieldType;
// import org.springframework.data.elasticsearch.annotations.Mapping;
// import org.springframework.data.elasticsearch.annotations.Setting;
//
// import lombok.AccessLevel;
// import lombok.Builder;
// import lombok.Getter;
// import lombok.NoArgsConstructor;
//
// @Mapping(mappingPath = "elastic/chat-mapping.json")
// @Setting(settingPath = "elastic/chat-setting.json")
// @NoArgsConstructor(access = AccessLevel.PROTECTED)
// @Getter
// @Builder
// @Document(indexName = "chates")
// public class ChatES {
//
// 	@Id
// 	private String id;
//
// 	private MessageType type;
//
// 	private String nickname;
//
// 	private String email;
//
// 	private String roomCode;
//
// 	private String message;
//
// 	@Field(type = FieldType.Date, format = {DateFormat.date_hour_minute_second_millis, DateFormat.epoch_millis})
// 	private Instant createdAt;
//
// 	private ChatES(MessageType type, String nickname, String email, String roomCode, String message) {
// 		this.type = type;
// 		this.nickname = nickname;
// 		this.email = email;
// 		this.roomCode = roomCode;
// 		this.message = message;
// 		this.createdAt = Instant.now();
// 	}
//
// 	public static ChatES of(MessageType type, String nickname, String email, String roomCode, String message) {
// 		return new ChatES(type, nickname, email, roomCode, message);
// 	}
//
// 	@Builder
// 	public ChatES(String id, MessageType type, String nickname, String email, String roomCode, String message,
// 		Instant createdAt) {
// 		this.id = id;
// 		this.type = type;
// 		this.nickname = nickname;
// 		this.email = email;
// 		this.roomCode = roomCode;
// 		this.message = message;
// 		this.createdAt = createdAt;
// 	}
// }
