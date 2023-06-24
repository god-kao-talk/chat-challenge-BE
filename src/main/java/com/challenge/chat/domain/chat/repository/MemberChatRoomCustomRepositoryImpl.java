package com.challenge.chat.domain.chat.repository;

import com.challenge.chat.domain.chat.entity.ChatRoom;
import com.challenge.chat.domain.chat.entity.MemberChatRoom;
import com.challenge.chat.domain.chat.entity.QMemberChatRoom;
import com.challenge.chat.domain.member.entity.Member;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.apache.kafka.common.protocol.types.Field;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

import static com.challenge.chat.domain.chat.entity.QMemberChatRoom.memberChatRoom;
@Repository
public class MemberChatRoomCustomRepositoryImpl implements MemberChatRoomCustomRepository{
    private final JPAQueryFactory queryFactory;
    private final EntityManager entityManager;

    public MemberChatRoomCustomRepositoryImpl(JPAQueryFactory queryFactory, EntityManager entityManager) {
        this.queryFactory = queryFactory;
        this.entityManager = entityManager;
    }


    @Override
    public Optional<MemberChatRoom> findByMemberAndRoom(Member member, ChatRoom room) {
        QMemberChatRoom qMemberChatRoom = QMemberChatRoom.memberChatRoom;
        MemberChatRoom memberChatRoom = queryFactory.selectFrom(qMemberChatRoom)
            .where(qMemberChatRoom.member.eq(member)
            .and(qMemberChatRoom.room.eq(room)))
        .fetchFirst();

        return Optional.ofNullable(memberChatRoom);
    }

    @Override
    public Optional<List<MemberChatRoom>> findByMember(Member member) {
        QMemberChatRoom qMemberChatRoom = QMemberChatRoom.memberChatRoom;
        BooleanExpression predicate = qMemberChatRoom.member.eq(member);

        List<MemberChatRoom> memberChatRooms = queryFactory.selectFrom(qMemberChatRoom)
                .where(predicate)
                .fetch();
        return Optional.ofNullable(memberChatRooms);
    }

    @Override
    public void MemberChatRoomSave(MemberChatRoom memberChatRoom) {
        entityManager.persist((memberChatRoom));

    }
}
