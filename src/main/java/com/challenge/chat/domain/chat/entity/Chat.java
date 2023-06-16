package com.challenge.chat.domain.chat.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.cassandra.core.cql.Ordering;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.time.Clock;
import java.time.Instant;
import java.util.Set;
import java.util.UUID;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@Table(value = "chat")
@Getter
@NoArgsConstructor
public class Chat {

	@PrimaryKeyColumn(value = "room_id", type = PrimaryKeyType.PARTITIONED, ordinal = 0)
	private String roomId;

	@PrimaryKeyColumn(name = "create_at", type = PrimaryKeyType.CLUSTERED, ordering = Ordering.DESCENDING, ordinal = 1)
	private Instant createdAt;

	@PrimaryKeyColumn(name = "chat_id", type = PrimaryKeyType.CLUSTERED, ordering = Ordering.DESCENDING, ordinal = 2)
	private UUID chatId;

	@Column
	private MessageType type;

	@Column
	private String sender;

	@Column(value = "user_id")
	private String userId;

	@Column
	private String message;

	private Chat(MessageType type, String sender, String userId, String roomId, String message) {
		this.chatId = UUID.randomUUID();
		this.createdAt = Instant.now();
		this.type = type;
		this.sender = sender;
		this.userId = userId;
		this.roomId = roomId;
		this.message = message;
	}

	public static Chat of(MessageType type, String sender, String userId, String roomId, String message) {
		return new Chat(type, sender, userId, roomId, message);
	}
}
