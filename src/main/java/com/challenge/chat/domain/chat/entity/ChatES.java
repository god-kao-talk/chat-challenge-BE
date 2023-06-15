package com.challenge.chat.domain.chat.entity;

import java.time.Instant;

import javax.persistence.Id;

import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Mapping;
import org.springframework.data.elasticsearch.annotations.Setting;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Mapping(mappingPath = "elastic/chat-mapping.json")
@Setting(settingPath = "elastic/chat-setting.json")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
@Document(indexName = "chates")
public class ChatES {

	@Id
	private String id;

	private MessageType type;

	private String sender;

	private String userId;

	private String roomId;

	private String message;

	@Field(type = FieldType.Date, format = {DateFormat.date_hour_minute_second_millis, DateFormat.epoch_millis})
	private Instant createdAt;

	private ChatES(MessageType type, String sender, String userId, String roomId, String message) {
		this.type = type;
		this.sender = sender;
		this.userId = userId;
		this.roomId = roomId;
		this.message = message;
		this.createdAt = Instant.now();
	}

	public static ChatES of(MessageType type, String sender, String userId, String roomId, String message) {
		return new ChatES(type, sender, userId, roomId, message);
	}

	@Builder
	public ChatES(String id, MessageType type, String sender, String userId, String roomId, String message,
		Instant createdAt) {
		this.id = id;
		this.type = type;
		this.sender = sender;
		this.userId = userId;
		this.roomId = roomId;
		this.message = message;
		this.createdAt = createdAt;
	}
}
