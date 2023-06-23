package com.challenge.chat.domain.member.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMemberFriend is a Querydsl query type for MemberFriend
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMemberFriend extends EntityPathBase<MemberFriend> {

    private static final long serialVersionUID = 2107346907L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMemberFriend memberFriend = new QMemberFriend("memberFriend");

    public final QMember friend;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QMember member;

    public QMemberFriend(String variable) {
        this(MemberFriend.class, forVariable(variable), INITS);
    }

    public QMemberFriend(Path<? extends MemberFriend> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMemberFriend(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMemberFriend(PathMetadata metadata, PathInits inits) {
        this(MemberFriend.class, metadata, inits);
    }

    public QMemberFriend(Class<? extends MemberFriend> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.friend = inits.isInitialized("friend") ? new QMember(forProperty("friend")) : null;
        this.member = inits.isInitialized("member") ? new QMember(forProperty("member")) : null;
    }

}

