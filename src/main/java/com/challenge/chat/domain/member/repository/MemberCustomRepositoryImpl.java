package com.challenge.chat.domain.member.repository;

import com.challenge.chat.domain.member.constant.SocialType;
import com.challenge.chat.domain.member.entity.Member;
import com.challenge.chat.domain.member.entity.QMember;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.Optional;

@Repository
public class MemberCustomRepositoryImpl implements MemberCustomRepository{

    private final JPAQueryFactory queryFactory;
    private final EntityManager entityManager;


    public MemberCustomRepositoryImpl(JPAQueryFactory queryFactory, EntityManager entityManager) {
        this.queryFactory = queryFactory;
        this.entityManager = entityManager;
    }

    @Override
    public Optional<Member> findByEmail(String email) {
        QMember qMember = QMember.member;
        Member member = queryFactory.selectFrom(qMember)
                .where(qMember.email.eq(email))
                .fetchFirst();
        return Optional.ofNullable(member);
    }

    @Override
    public Optional<Member> findByRefreshToken(String refreshToken) {
        QMember qMember = QMember.member;
        Member member = queryFactory.selectFrom(qMember)
                .where(qMember.refreshToken.eq(refreshToken))
                .fetchFirst();
        return Optional.ofNullable(member);
    }

    @Override
    public Optional<Member> findBySocialTypeAndSocialId(SocialType socialType, String socialId) { QMember qMember = QMember.member;
        Member member = queryFactory.selectFrom(qMember)
                .where(qMember.socialType.eq(socialType).and(qMember.socialId.eq(socialId)))
                .fetchFirst();
        return Optional.ofNullable(member);
    }


    @Override
    public void memberSave(Member member) {
        entityManager.persist(member);
    }

    @Override
    public Member createdUserSave(Member member) {
        entityManager.persist(member);
        return member;
    }
}
