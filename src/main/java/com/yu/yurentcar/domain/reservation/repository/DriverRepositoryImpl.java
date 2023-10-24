package com.yu.yurentcar.domain.reservation.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Repository;

import static com.yu.yurentcar.domain.reservation.entity.QDriver.driver;


@Log4j2
@Repository
@RequiredArgsConstructor
public class DriverRepositoryImpl implements DriverRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Long deleteAllDriversByReservationId(Long reservationId) {
        return queryFactory
                .delete(driver)
                .where(driver.reservation.reservationId.eq(reservationId))
                .execute();
    }
}