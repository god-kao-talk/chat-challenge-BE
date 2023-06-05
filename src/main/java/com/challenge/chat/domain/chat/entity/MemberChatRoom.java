package com.challenge.chat.domain.chat.entity;

import com.challenge.chat.domain.member.entity.Member;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "memberchatroom")
@Getter
@NoArgsConstructor
public class MemberChatRoom {
	@Id
	private String id;

	private String roomId;
	private String memberId;

	public MemberChatRoom(ChatRoom room, Member member) {
		this.roomId = room.getRoomId();
		this.memberId = member.getEmail();
	}
}
