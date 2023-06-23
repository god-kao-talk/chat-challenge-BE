package com.challenge.chat.domain.member.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMember is a Querydsl query type for Member
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMember extends EntityPathBase<Member> {

    private static final long serialVersionUID = -773000483L;

    public static final QMember member = new QMember("member1");

    public final StringPath email = createString("email");

    public final ListPath<MemberFriend, QMemberFriend> friendList = this.<MemberFriend, QMemberFriend>createList("friendList", MemberFriend.class, QMemberFriend.class, PathInits.DIRECT2);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath imageUrl = createString("imageUrl");

    public final StringPath nickname = createString("nickname");

    public final StringPath password = createString("password");

    public final StringPath refreshToken = createString("refreshToken");

    public final EnumPath<com.challenge.chat.domain.member.constant.MemberRole> role = createEnum("role", com.challenge.chat.domain.member.constant.MemberRole.class);

    public final ListPath<com.challenge.chat.domain.chat.entity.MemberChatRoom, com.challenge.chat.domain.chat.entity.QMemberChatRoom> roomList = this.<com.challenge.chat.domain.chat.entity.MemberChatRoom, com.challenge.chat.domain.chat.entity.QMemberChatRoom>createList("roomList", com.challenge.chat.domain.chat.entity.MemberChatRoom.class, com.challenge.chat.domain.chat.entity.QMemberChatRoom.class, PathInits.DIRECT2);

    public final StringPath socialId = createString("socialId");

    public final EnumPath<com.challenge.chat.domain.member.constant.SocialType> socialType = createEnum("socialType", com.challenge.chat.domain.member.constant.SocialType.class);

    public QMember(String variable) {
        super(Member.class, forVariable(variable));
    }

    public QMember(Path<? extends Member> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMember(PathMetadata metadata) {
        super(Member.class, metadata);
    }

}

