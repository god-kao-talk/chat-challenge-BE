package com.challenge.chat.domain.chat.entity;

import com.challenge.chat.domain.chat.dto.ChatDto;
import com.challenge.chat.domain.member.entity.Member;
import com.challenge.chat.global.entity.TimeStamped;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "TB_CHAT")
@Getter
@NoArgsConstructor
public class Chat extends TimeStamped {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "room_Id")
	private ChatRoom room;

	private String sender;

	private String message;

	private String email;

	@Enumerated(EnumType.STRING)
	private MessageType type;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	private Member member;

	public Chat(ChatDto chatDto, ChatRoom room, Member member, MessageType type) {
		this.sender = chatDto.getSender();
		this.message = chatDto.getMessage();
		this.room = room;
		this.member = member;
		this.email = member.getEmail();
		this.type = type;
	}
}
