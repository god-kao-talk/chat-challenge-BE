package com.challenge.chat.domain.chat.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMemberChatRoom is a Querydsl query type for MemberChatRoom
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMemberChatRoom extends EntityPathBase<MemberChatRoom> {

    private static final long serialVersionUID = 286826382L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMemberChatRoom memberChatRoom = new QMemberChatRoom("memberChatRoom");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final com.challenge.chat.domain.member.entity.QMember member;

    public final QChatRoom room;

    public QMemberChatRoom(String variable) {
        this(MemberChatRoom.class, forVariable(variable), INITS);
    }

    public QMemberChatRoom(Path<? extends MemberChatRoom> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMemberChatRoom(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMemberChatRoom(PathMetadata metadata, PathInits inits) {
        this(MemberChatRoom.class, metadata, inits);
    }

    public QMemberChatRoom(Class<? extends MemberChatRoom> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new com.challenge.chat.domain.member.entity.QMember(forProperty("member")) : null;
        this.room = inits.isInitialized("room") ? new QChatRoom(forProperty("room")) : null;
    }

}

