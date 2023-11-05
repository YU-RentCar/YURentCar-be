package com.yu.yurentcar.domain.reservation.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yu.yurentcar.domain.car.dto.CarEventResponseDto;
import com.yu.yurentcar.domain.car.dto.CarResponseDto;
import com.yu.yurentcar.domain.car.dto.CarSpecDto;
import com.yu.yurentcar.domain.car.entity.QCarSpecification;
import com.yu.yurentcar.domain.reservation.dto.ReservationBranchDto;
import com.yu.yurentcar.domain.reservation.dto.ReservationDatesDto;
import com.yu.yurentcar.domain.reservation.dto.ReservationDetailDto;
import com.yu.yurentcar.domain.reservation.dto.ReservationListResponseDto;
import com.yu.yurentcar.domain.reservation.entity.Reservation;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

import static com.yu.yurentcar.domain.branch.entity.QBranch.branch;
import static com.yu.yurentcar.domain.car.entity.QCar.car;
import static com.yu.yurentcar.domain.car.entity.QCarEvent.carEvent;
import static com.yu.yurentcar.domain.car.entity.QCarSpecification.carSpecification;
import static com.yu.yurentcar.domain.reservation.entity.QDriver.driver;
import static com.yu.yurentcar.domain.reservation.entity.QReservation.reservation;
import static com.yu.yurentcar.domain.reservation.entity.QReview.review;
import static com.yu.yurentcar.domain.user.entity.QUser.user;


@Log4j2
@Repository
@RequiredArgsConstructor
public class ReservationRepositoryImpl implements ReservationRepositoryCustom {
    QCarSpecification carSpec = carSpecification;
    private final JPAQueryFactory queryFactory;

    @Override
    public List<CarEventResponseDto> getAccidentListByUsername(String username) {
        return queryFactory.select(Projections.constructor(CarEventResponseDto.class,
                        carEvent.title,
                        carEvent.eventDate,
                        carEvent.content))
                .from(car)
                .innerJoin(car, carEvent.car)
                .where(car.eq(
                        findLatestReservationInfo(username)
                                .select(reservation.car)).and(carEvent.isRepair.eq(false)))
                .orderBy(carEvent.eventDate.asc())
                .fetch();
    }

    @Override
    public List<CarEventResponseDto> getRepairListByUsername(String username) {
        return queryFactory.select(Projections.constructor(CarEventResponseDto.class,
                        carEvent.title,
                        carEvent.eventDate,
                        carEvent.content))
                .from(car)
                .innerJoin(car, carEvent.car)
                .where(car.eq(
                        findLatestReservationInfo(username)
                                .select(reservation.car)).and(carEvent.isRepair.eq(true)))
                .orderBy(carEvent.eventDate.asc())
                .fetch();
    }

    @Override
    public Integer getPriceByUsername(String username) {
        return findLatestReservationInfo(username)
                .select(reservation.reservationPrice).fetchFirst();
    }

    @Override
    public CarResponseDto getCarDtoByUsername(String username) {
        return queryFactory
                .select(Projections.constructor(CarResponseDto.class, car.carSpec.carName, car.carNumber, car.totalDistance))
                .from(car)
                .where(car.eq(findLatestReservationInfo(username)
                        .select(reservation.car))).fetchFirst();
    }

    @Override
    public CarSpecDto getCarSpecificationDtoByUsername(String username) {
        return queryFactory
                .select(Projections.constructor(CarSpecDto.class, carSpec.oilType, carSpec.releaseDate, car.createdAt, carSpec.maxPassenger, carSpec.transmission, carSpec.carBrand, carSpec.isKorean))
                .from(car)
                .innerJoin(car.carSpec, carSpec)
                .where(car.carSpec.eq(queryFactory
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

    @Override
    public List<String> findNowReservationDriversByUsername(String username) {
        return queryFactory
                .select(driver.driverName)
                .from(driver)
                .where(driver.reservation.eq(findLatestReservationInfo(username).select(reservation)))
                .fetch();
    }

    @Override
    public List<ReservationListResponseDto> getReservationListByUsername(String username) {
        return queryFactory
                .select(Projections.constructor(ReservationListResponseDto.class,
                        reservation.reservationId,
                        reservation.car.carSpec.carName,
                        reservation.car.carNumber,
                        car.totalDistance,
                        reservation.startDate,
                        reservation.endDate,
                        branch.branchName,
                        reservation.reservationPrice,
                        review.reviewId.isNotNull()))
                .from(reservation)
                .leftJoin(review).on(reservation.eq(review.reservation))
                .innerJoin(reservation.car.branch, branch)
                .where(reservation.user.username.eq(username))
                .where(reservation.endDate.before(LocalDateTime.now()))
                .orderBy(reservation.endDate.desc())
                .fetch();
    }

    @Override
    public Reservation findRecentReservationsByCarId(Long carId) {
        return queryFactory
                .selectFrom(reservation)
                .where(reservation.car.carId.eq(carId))
                .orderBy(reservation.endDate.desc())
                .fetchFirst();
    }

    private JPAQuery<?> findLatestReservationInfo(String username) {
        log.info("username = " + username);
        return queryFactory
                .from(reservation)
                .where(reservation.user.eq(queryFactory
                                .selectFrom(user)
                                .where(user.username.eq(username)).fetchOne())
                        .and(reservation.endDate.gt(LocalDateTime.now())))
                .limit(1);
    }

    @Override
    public List<ReservationBranchDto> getReservationListByBranchId(Long branchId, Boolean isDone) {
        JPAQuery<ReservationBranchDto> query = queryFactory
                .select(Projections.constructor(ReservationBranchDto.class,
                        reservation.user.nickname,
                        reservation.reservationId,
                        reservation.car.carNumber))
                .from(reservation)
                .where(reservation.car.branch.branchId.eq(branchId));
        if (isDone != null) {
            if(isDone)
                query = query.where(reservation.endDate.before(LocalDateTime.now()));
            else
                query = query.where(reservation.endDate.after(LocalDateTime.now()));
        }

        return query.fetch();
    }

    @Override
    public ReservationDatesDto getReservationStartDateAndEndDateByReservationId(Long reservationId) {
        return queryFactory
                .select(Projections.constructor(ReservationDatesDto.class,
                        reservation.startDate,
                        reservation.endDate))
                .from(reservation)
                .where(reservation.reservationId.eq(reservationId))
                .limit(1)
                .fetchFirst();
    }
}