package com.yu.yurentcar.domain.car.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yu.yurentcar.domain.car.dto.CarDetailsResponseDto;
import com.yu.yurentcar.domain.car.dto.UsableCarResponseDto;
import com.yu.yurentcar.domain.car.dto.UsableCarSearchRequestDto;
import com.yu.yurentcar.domain.car.entity.QCarSpecification;
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

    public JPAQuery<Long> isCarUsable(String siDo, String branchName, LocalDateTime startTime, LocalDateTime endTime) {
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
                .where(reservation.startDate.before(endOffset).and(reservation.endDate.after(startOffset))
                );
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
}
