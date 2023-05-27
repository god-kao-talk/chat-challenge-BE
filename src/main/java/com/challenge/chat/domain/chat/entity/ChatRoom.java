package com.challenge.chat.domain.chat.entity;

import java.util.ArrayList;
import java.util.List;

import com.challenge.chat.global.entity.TimeStamped;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class ChatRoom extends TimeStamped {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ROOM_ID")
	private Long id;

	@Column(name = "ROOM_UUID")
	private String roomId;

	@Column(nullable = false)
	private String roomName;

	@OneToMany(mappedBy = "room", orphanRemoval = true, cascade = CascadeType.ALL)
	private List<MemberChatRoom> memberList = new ArrayList<>();

	private ChatRoom(String roomId, String roomName) {
		this.roomId = roomId;
		this.roomName = roomName;
	}

	public static ChatRoom of(String roomId, String roomName) {
		return new ChatRoom(roomId, roomName);
	}
}