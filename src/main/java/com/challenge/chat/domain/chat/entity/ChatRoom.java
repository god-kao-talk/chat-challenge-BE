package com.challenge.chat.domain.chat.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.ColumnDefault;

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

@Entity(name = "TB_CHATROOM")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ChatRoom {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String roomId;

	@Column(nullable = false)
	private String roomName;

	@OneToMany(mappedBy = "room", cascade = CascadeType.REMOVE)
	private List<Chat> chatLists = new ArrayList<>();

	@Column(nullable = false)
	private String host;

	@Column
	private String email;

	@ColumnDefault("0")
	@Column(nullable = false)
	private Long headCount;

	public ChatRoom(String roomName, String host, String email) {
		this.roomId = UUID.randomUUID().toString();
		this.roomName = roomName;
		this.host = host;
		this.email = email;
		this.headCount = 0L;
	}

	public void updateCount(Long headCount) {
		this.headCount = headCount;
	}

}