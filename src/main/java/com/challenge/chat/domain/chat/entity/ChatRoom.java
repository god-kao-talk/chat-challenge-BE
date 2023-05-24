package com.challenge.chat.domain.chat.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.challenge.chat.global.entity.TimeStamped;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
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

	public ChatRoom(String roomName) {
		this.roomId = UUID.randomUUID().toString();
		this.roomName = roomName;
	}
}