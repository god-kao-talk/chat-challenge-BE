package com.challenge.chat.domain.chat.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.cassandra.core.cql.Ordering;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

import javax.persistence.Id;

@Table(value = "chat_room")
@Getter
@NoArgsConstructor
public class ChatRoom {
	@PrimaryKeyColumn(value = "room_id", type = PrimaryKeyType.PARTITIONED, ordinal = 0)
	private String roomId;

	@PrimaryKeyColumn(name = "create_at", type = PrimaryKeyType.CLUSTERED, ordering = Ordering.DESCENDING, ordinal = 1)
	private Instant createdAt;

	@Column
	private String roomName;

	@Column
	private Set<String> memberEmailList;

	private ChatRoom(String roomId, String roomName) {
		this.createdAt = Instant.now();
		this.roomId = roomId;
		this.roomName = roomName;
	}

	public static ChatRoom of(String roomId, String roomName) {
		return new ChatRoom(roomId, roomName);
	}
}