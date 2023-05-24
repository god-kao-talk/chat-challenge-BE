package com.challenge.chat.domain.chat.entity;

import com.challenge.chat.domain.member.entity.Member;

import jakarta.persistence.Entity;
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
public class MemberChatRoom {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "ROOM_ID")
	private ChatRoom room;

	@ManyToOne
	@JoinColumn(name = "MEMBER_ID")
	private Member member;

	public MemberChatRoom(ChatRoom room, Member member) {
		this.room = room;
		this.member = member;
		room.getMemberList().add(this);
		member.getRoomList().add(this);
	}
}
