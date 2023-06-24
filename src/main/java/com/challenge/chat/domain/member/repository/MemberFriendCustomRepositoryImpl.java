package com.challenge.chat.domain.member.repository;

import com.challenge.chat.domain.member.entity.Member;
import com.challenge.chat.domain.member.entity.MemberFriend;
import com.challenge.chat.domain.member.entity.QMemberFriend;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
public class MemberFriendCustomRepositoryImpl implements MemberFriendCustomRepository{
    private final JPAQueryFactory queryFactory;
    private final EntityManager entityManager;

    public MemberFriendCustomRepositoryImpl(JPAQueryFactory queryFactory, EntityManager entityManager) {
        this.queryFactory = queryFactory;
        this.entityManager = entityManager;
    }


    @Override
    public Optional<MemberFriend> findByMemberAndFriend(Member member, Member friend) {
        QMemberFriend qMemberFriend = QMemberFriend.memberFriend;
        MemberFriend memberFriend = queryFactory.selectFrom(qMemberFriend)
                .where(qMemberFriend.member.eq(member)
                .and(qMemberFriend.friend.eq(friend)))
        .fetchFirst();
        return Optional.ofNullable(memberFriend);
    }

    @Override
    public Optional<List<MemberFriend>> findByMember(Member member) {
        QMemberFriend qMemberFriend = QMemberFriend.memberFriend;
        List<MemberFriend> memberFriends = queryFactory.selectFrom(qMemberFriend)
                .where(qMemberFriend.member.eq(member))
                .fetch();
        return Optional.ofNullable(memberFriends);
    }

    @Override
    public void MemberFriendSave(MemberFriend memberFriend) {
        QMemberFriend qMemberFriend = QMemberFriend.memberFriend;
        entityManager.persist(memberFriend);
        entityManager.flush();

    }
}
