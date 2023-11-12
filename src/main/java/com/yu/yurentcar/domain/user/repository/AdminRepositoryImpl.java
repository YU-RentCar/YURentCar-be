package com.yu.yurentcar.domain.user.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Repository;

import static com.yu.yurentcar.domain.reservation.entity.QReservation.reservation;
import static com.yu.yurentcar.domain.user.entity.QAdmin.admin;

@Log4j2
@RequiredArgsConstructor
@Repository
public class AdminRepositoryImpl implements AdminRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public Boolean isReservationByAdminBranch(Long reservationId, String adminUsername) {
        return !queryFactory
                .selectFrom(reservation)
                .where(reservation.reservationId.eq(reservationId))
                .where(reservation.car.branch.eq(
                        queryFactory.select(admin.branch)
                                .from(admin)
                                .where(admin.username.eq(adminUsername))
                ))
                .limit(1)
                .fetch().isEmpty();
    }
}
