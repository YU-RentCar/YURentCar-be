package com.yu.yurentcar.domain.car.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yu.yurentcar.domain.car.dto.*;
import com.yu.yurentcar.domain.car.entity.QCarSpecification;
import com.yu.yurentcar.global.SiDoType;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

import static com.yu.yurentcar.domain.branch.entity.QBranch.branch;
import static com.yu.yurentcar.domain.car.entity.QCar.car;
import static com.yu.yurentcar.domain.car.entity.QCarSpecification.carSpecification;
import static com.yu.yurentcar.domain.reservation.entity.QReservation.reservation;

@Log4j2
@RequiredArgsConstructor
@Repository
public class CarRepositoryImpl implements CarRepositoryCustom {
    QCarSpecification carSpec = carSpecification;

    private final JPAQueryFactory queryFactory;

    @Override
    public List<UsableCarResponseDto> searchUsableCar(UsableCarSearchRequestDto requestDto) {
        JPAQuery<UsableCarResponseDto> query = queryFactory.select(Projections.constructor(UsableCarResponseDto.class,
                        carSpec.carName,
                        car.carNumber,
                        car.totalDistance
                ))
                .from(car)
                .innerJoin(car.carSpec, carSpec);
        if(!requestDto.getCarSizes().isEmpty())
            query.where(carSpec.carSize.in(requestDto.getCarSizes()));
        if(!requestDto.getOilTypes().isEmpty())
                query.where(carSpec.oilType.in(requestDto.getOilTypes()));
        if(!requestDto.getTransmissions().isEmpty())
                query.where(carSpec.transmission.in(requestDto.getTransmissions()));
        return query.where(carSpec.maxPassenger.gt(requestDto.getMinCount()))
                .where(car.carId.notIn(isCarUsable(requestDto.getSiDo(), requestDto.getBranchName(), requestDto.getStartDate(), requestDto.getEndDate())))
                .orderBy(carSpec.carName.asc())
                .fetch();
    }

    public JPAQuery<Long> isCarUsable(SiDoType siDo, String branchName, LocalDateTime startTime, LocalDateTime endTime) {
        return queryFactory.selectDistinct(reservation.car.carId)
                .from(reservation)
                .innerJoin(reservation.car, car)
                //특정 지점의 차만 필터링
                .where(
                        car.branch.in(
                                queryFactory.selectDistinct(branch)
                                        .from(branch)
                                        .where(branch.siDo.eq(siDo).and(branch.branchName.eq(branchName)))
                        )
                )
                .where(getUsableDateFilter(startTime, endTime));
    }

    public BooleanExpression getUsableDateFilter(LocalDateTime startTime, LocalDateTime endTime) {
        LocalDateTime startOffset, endOffset;
        if(startTime.getHour() < 12) {
            startOffset = startTime.minusDays(1);
            startOffset = LocalDateTime.of(startOffset.getYear(), startOffset.getMonth(), startOffset.getDayOfMonth(), 12, 0);
        }
        else {
            startOffset = LocalDateTime.of(startTime.getYear(), startTime.getMonth(), startTime.getDayOfMonth(), 0, 0);
        }
        if(endTime.getHour() < 12) {
            endOffset = endTime.plusDays(1);
            endOffset = LocalDateTime.of(endOffset.getYear(), endOffset.getMonth(), endOffset.getDayOfMonth(), 12, 0);
        }
        else {
            endOffset = endTime.plusDays(2);
            endOffset = LocalDateTime.of(endOffset.getYear(), endOffset.getMonth(), endOffset.getDayOfMonth(), 0, 0);
        }
        log.info("start offset : " + startOffset);
        log.info("end offset : " + endOffset);
        return reservation.startDate.before(endOffset).and(reservation.endDate.after(startOffset));
    }

    @Override
    public CarDetailsResponseDto findCarDetailsByCarNumber(String carNumber) {
        return queryFactory.select(Projections.constructor(CarDetailsResponseDto.class,
                carSpec.carName,
                car.carNumber,
                carSpec.carSize,
                carSpec.oilType,
                carSpec.transmission,
                carSpec.maxPassenger
                ))
                .from(car)
                .innerJoin(car.carSpec, carSpec)
                .where(car.carNumber.eq(carNumber))
                .fetchOne();
    }

    @Override
    public CarResponseDto findCarResponseDtoByCarNumber(String carNumber) {
        return queryFactory
                .select(Projections.constructor(CarResponseDto.class,car.carSpec.carName,car.carNumber,car.totalDistance))
                .from(car)
                .where(car.carNumber.eq(carNumber))
                .fetchFirst();
    }

    @Override
    public CarSpecDto findCarSpecByCarNumber(String carNumber) {
        return queryFactory
                .select(Projections.constructor(CarSpecDto.class,carSpec.oilType,carSpec.releaseDate,car.createdAt,carSpec.maxPassenger,carSpec.transmission,carSpec.carBrand,carSpec.isKorean))
                .from(car)
                .innerJoin(car.carSpec,carSpec)
                .where(car.carNumber.eq(carNumber))
                .fetchFirst();
    }

    @Override
    public List<String> findAccidentListByCarNumber(String carNumber) {
        return queryFactory
                .select(car.accidentList)
                .from(car)
                .where(car.carNumber.eq(carNumber))
                .fetchFirst();
    }

    @Override
    public List<String> findRepairListByCarNumber(String carNumber) {
        return queryFactory
                .select(car.repairList)
                .from(car)
                .where(car.carNumber.eq(carNumber))
                .fetchFirst();
    }

    @Override
    public Boolean usableByCarNumberAndDate(String carNumber, LocalDateTime startTime, LocalDateTime endTime) {
        return queryFactory.selectDistinct(reservation.car.carId)
                .from(reservation)
                .innerJoin(reservation.car, car)
                .where(car.carNumber.eq(carNumber))
                .where(getUsableDateFilter(startTime, endTime))
                .limit(1).fetch().isEmpty();
    }

    @Override
    public List<CarResponseDto> findCarsByCarNumbers(String[] carNumber) {
        return queryFactory
                .select(Projections.constructor(CarResponseDto.class,car.carSpec.carName,car.carNumber,car.totalDistance))
                .from(car)
                .where(car.carNumber.in(carNumber))
                .fetch();
    }
}
