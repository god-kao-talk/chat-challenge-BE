package com.challenge.chat.domain.chat.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
@Getter
@NoArgsConstructor
public class ChatRoom extends TimeStamped {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ROOM_ID")
	private Long id;

	private String roomCode;

	@Column(nullable = false)
	private String roomName;

	@OneToMany(mappedBy = "room", orphanRemoval = true, cascade = CascadeType.ALL)
	private List<MemberChatRoom> memberList;

	private ChatRoom(String roomName) {
		this.roomCode = UUID.randomUUID().toString();
		this.roomName = roomName;
	}

	public static ChatRoom of(String roomName) {
		return new ChatRoom(roomName);
	}
}
