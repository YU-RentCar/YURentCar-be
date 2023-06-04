package com.yu.yurentcar.domain.reservation.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yu.yurentcar.domain.reservation.dto.PointDetailsResponseDto;
import com.yu.yurentcar.domain.user.entity.QUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.yu.yurentcar.domain.reservation.entity.QPoint.point;

@Log4j2
@Repository
@RequiredArgsConstructor
public class PointRepositoryImpl implements PointRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<PointDetailsResponseDto> findAllPointByUsername(String username) {
        return queryFactory
                .select(Projections.constructor(PointDetailsResponseDto.class,
                        point.price,
                        point.reason,
                        point.createdAt))
                .from(point)
                .innerJoin(point.userId, QUser.user)
                .where(QUser.user.username.eq(username))
                .orderBy(point.createdAt.desc()).fetch();
    }

}
