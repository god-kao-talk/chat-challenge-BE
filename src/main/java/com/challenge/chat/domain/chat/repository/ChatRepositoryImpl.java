package com.challenge.chat.domain.chat.repository;

import com.challenge.chat.domain.chat.entity.Chat;
import com.challenge.chat.domain.chat.entity.ChatRoom;
import com.challenge.chat.domain.chat.entity.QChat;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class ChatRepositoryImpl implements  ChatCustomRepository{
    private final JPAQueryFactory queryFactory;
    private final EntityManager entityManager;

    @Autowired
    public ChatRepositoryImpl(JPAQueryFactory queryFactory, EntityManager entityManager) {
        this.queryFactory = queryFactory;
        this.entityManager = entityManager;
    }

    @Override
    public List<Chat> findByRoom(ChatRoom room) {
        QChat qChat = QChat.chat;

        return queryFactory.selectFrom(qChat)
                .where(qChat.room.eq(room))
                .fetch();
    }

    @Override
    public void save(Chat chat) {
        entityManager.persist(chat);
    }
}
