package com.challenge.chat.domain.chat.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.challenge.chat.domain.member.entity.Member;
import com.challenge.chat.global.TimeStamped;

@Getter
@Entity
@NoArgsConstructor
public class Chat extends TimeStamped {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Enumerated(EnumType.STRING)
	private MessageType type;

	@ManyToOne
	@JoinColumn(name = "MEMBER_ID")
	private Member member;

	@ManyToOne
	@JoinColumn(name = "ROOM_ID")
	private ChatRoom room;

	private String message;

	private Chat(MessageType type, Member member, ChatRoom chatRoom, String message) {
		this.type = type;
		this.member = member;
		this.room = chatRoom;
		this.message = message;
	}

	public static Chat of(MessageType type, Member member, ChatRoom chatRoom, String message) {
		return new Chat(type, member, chatRoom, message);
	}
}
