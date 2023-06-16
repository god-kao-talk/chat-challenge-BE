package com.challenge.chat.domain.chat.entity;

import java.util.UUID;

import javax.persistence.Id;

import org.springframework.data.cassandra.core.cql.Ordering;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;


@Table(value = "member_chat_room")
@Getter
@NoArgsConstructor
public class MemberChatRoom {
	@PrimaryKeyColumn(value = "member_email", type = PrimaryKeyType.PARTITIONED, ordinal = 0)
	private String memberEmail;

	@PrimaryKeyColumn(name = "room_id", type = PrimaryKeyType.CLUSTERED, ordering = Ordering.DESCENDING, ordinal = 1)
	private String roomId;

	public MemberChatRoom(String roomId, String email) {
		this.roomId = roomId;
		this.memberEmail = email;
	}
}
