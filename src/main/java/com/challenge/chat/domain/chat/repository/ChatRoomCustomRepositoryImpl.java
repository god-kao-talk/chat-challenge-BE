package com.challenge.chat.domain.chat.repository;

import com.challenge.chat.domain.chat.entity.ChatRoom;
import com.challenge.chat.domain.chat.entity.QChatRoom;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.Optional;

@Repository
public class ChatRoomCustomRepositoryImpl implements ChatRoomCustomRepository{

    private final JPAQueryFactory queryFactory;
    private final EntityManager entityManager;

    @Autowired
    public ChatRoomCustomRepositoryImpl(JPAQueryFactory queryFactory, EntityManager entityManager) {
        this.queryFactory = queryFactory;
        this.entityManager = entityManager;
    }
    @Override
    public Optional<ChatRoom> findByRoomCode(String roomCode) {
        QChatRoom qChatRoom = QChatRoom.chatRoom;
        return Optional.ofNullable(queryFactory.selectFrom(qChatRoom)
                .where(qChatRoom.roomCode.eq(roomCode))
                .fetchOne());
    }

    @Override
    public void chatRoomSave(ChatRoom chatRoom){
        entityManager.persist(chatRoom);
    }
}
