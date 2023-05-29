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

@Entity
@Getter
@NoArgsConstructor
public class Chat extends TimeStamped {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String message;

	@Enumerated(EnumType.STRING)
	private MessageType type;

	@ManyToOne
	@JoinColumn(name = "MEMBER_ID")
	private Member member;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ROOM_ID")
	private ChatRoom room;

	public Chat(ChatDto chatDto, ChatRoom room, Member member, MessageType type) {
		this.message = chatDto.getMessage();
		this.room = room;
		this.member = member;
		this.type = type;
	}
}
