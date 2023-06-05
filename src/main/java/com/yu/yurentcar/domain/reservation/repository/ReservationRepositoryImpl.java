package com.yu.yurentcar.domain.reservation.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yu.yurentcar.domain.car.dto.CarResponseDto;
import com.yu.yurentcar.domain.car.dto.CarSpecDto;
import com.yu.yurentcar.domain.reservation.dto.ReservationDetailDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

import static com.yu.yurentcar.domain.branch.entity.QBranch.branch;
import static com.yu.yurentcar.domain.car.entity.QCar.car;
import static com.yu.yurentcar.domain.car.entity.QCarSpecification.carSpecification;
import static com.yu.yurentcar.domain.reservation.entity.QReservation.reservation;
import static com.yu.yurentcar.domain.user.entity.QUser.user;

@Log4j2
@Repository
@RequiredArgsConstructor
public class ReservationRepositoryImpl implements ReservationRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<String> getAccidentListByUsername(String username) {
        return queryFactory
                .select(car.accidentList)
                .from(car)
                .where(car.eq(
                        findLatestReservationInfo(username)
                                .select(reservation.car))
                ).fetchFirst();
    }

    @Override
    public List<String> getRepairListByUsername(String username) {
        return queryFactory
                .select(car.repairList)
                .from(car)
                .where(car.eq(
                        findLatestReservationInfo(username)
                                .select(reservation.car))
                ).fetchFirst();
    }

    @Override
    public Integer getPriceByUsername(String username) {
        return findLatestReservationInfo(username)
                .select(reservation.reservationPrice).fetchFirst();
    }

    @Override
    public CarResponseDto getCarDtoByUsername(String username) {
        return queryFactory
                .select(Projections.constructor(CarResponseDto.class, car.carNumber, car.totalDistance, car.createdAt))
                .from(car)
                .where(car.eq(findLatestReservationInfo(username)
                        .select(reservation.car))).fetchFirst();
    }

    @Override
    public CarSpecDto getCarSpecificationDtoByUsername(String username) {
        return queryFactory
                .select(Projections.constructor(CarSpecDto.class, carSpecification.carName, carSpecification.carSize, carSpecification.oilType, carSpecification.releaseDate, carSpecification.maxPassenger, carSpecification.transmission, carSpecification.carBrand, carSpecification.isKorean))
                .from(carSpecification)
                .where(carSpecification.eq(queryFactory
                        .select(car.carSpec)
                        .from(car)
                        .where(car.eq(
                                findLatestReservationInfo(username)
                                        .select(reservation.car))
                        ).fetchOne())).fetchFirst();
    }

    @Override
    public Point getBranchPointByUsername(String username) {
        return queryFactory
                .select(Projections.constructor(Point.class, branch.latitude, branch.longitude))
                .from(branch)
                .where(branch.eq(queryFactory.select(car.branch)//car의 branchId 가져옴
                        .from(car)
                        //유저네임으로 예약테이블 가져와서 carId가져옴
                        .where(car.eq(findLatestReservationInfo(username).select(reservation.car)))))
                .fetchFirst();
    }

    @Override
    public ReservationDetailDto findNowReservationDetailByUsername(String username) {
        return queryFactory
                .select(Projections.constructor(ReservationDetailDto.class, reservation.startDate, reservation.endDate, branch.branchName, carSpecification.carName, car.carNumber))
                .from(reservation)
                .innerJoin(reservation.car, car)
                .innerJoin(car.carSpec, carSpecification)
                .innerJoin(car.branch, branch)
                .where(reservation.eq(findLatestReservationInfo(username).select(reservation)))
                .fetchFirst();
    }

    private JPAQuery<?> findLatestReservationInfo(String username) {
        log.info("username = " + username);
        return queryFactory
                .from(reservation)
                .where(reservation.user.eq(queryFactory
                                .selectFrom(user)
                                .where(user.username.eq(username)).fetchOne())
                        .and(reservation.startDate.gt(LocalDateTime.now())))
                .limit(1);
    }
}