package com.yu.yurentcar.domain.car.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yu.yurentcar.domain.car.dto.CarEventResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.yu.yurentcar.domain.car.entity.QCarEvent.carEvent;

@Log4j2
@RequiredArgsConstructor
@Repository
public class CarEventRepositoryImpl implements CarEventRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<CarEventResponseDto> getRepairListByCarNumber(String carNumber) {
        return queryFactory.select(Projections.constructor(CarEventResponseDto.class,
                        carEvent.title,
                        carEvent.eventDate,
                        carEvent.content))
                .from(carEvent)
                .where(carEvent.car.carNumber.eq(carNumber).and(carEvent.isRepair.isTrue()))
                .orderBy(carEvent.eventDate.asc())
                .fetch();
    }

    @Override
    public List<CarEventResponseDto> getAccidentListByCarNumber(String carNumber) {
        return queryFactory.select(Projections.constructor(CarEventResponseDto.class,
                        carEvent.title,
                        carEvent.eventDate,
                        carEvent.content))
                .from(carEvent)
                .where(carEvent.car.carNumber.eq(carNumber).and(carEvent.isRepair.isFalse()))
                .orderBy(carEvent.eventDate.asc())
                .fetch();
    }

    @Override
    public Long deleteRepairListByCarId(Long carId) {
        return queryFactory.delete(carEvent)
                .where(carEvent.car.carId.eq(carId).and(carEvent.isRepair.isTrue()))
                .execute();
    }

    @Override
    public Long deleteAccidentListByCarId(Long carId) {
        return queryFactory.delete(carEvent)
                .where(carEvent.car.carId.eq(carId).and(carEvent.isRepair.isFalse()))
                .execute();
    }


}
