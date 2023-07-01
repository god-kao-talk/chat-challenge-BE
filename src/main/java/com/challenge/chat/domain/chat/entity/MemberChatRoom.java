package com.challenge.chat.domain.chat.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.challenge.chat.domain.member.entity.Member;

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

	private MemberChatRoom(ChatRoom room, Member member) {
		this.room = room;
		this.member = member;
		// room.getMemberList().add(this);
		// member.getRoomList().add(this);
	}

	public static MemberChatRoom of(ChatRoom room, Member member) {
		return new MemberChatRoom(room, member);
	}
}
