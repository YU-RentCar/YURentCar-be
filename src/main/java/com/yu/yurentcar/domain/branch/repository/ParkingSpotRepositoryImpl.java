package com.yu.yurentcar.domain.branch.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yu.yurentcar.domain.branch.dto.ParkingSpotResponseDto;
import com.yu.yurentcar.global.SiDoType;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.yu.yurentcar.domain.branch.entity.QParkingSpot.parkingSpot;

@Log4j2
@Repository
@RequiredArgsConstructor
public class ParkingSpotRepositoryImpl implements ParkingSpotRepositoryCustom {
    private final BranchRepositoryCustom branchRepository;

    private final JPAQueryFactory queryFactory;

    @Override
    public Long deleteAllParkingSpotByBranchId(Long branchId) {
        return queryFactory
                .delete(parkingSpot)
                .where(parkingSpot.branch.branchId.eq(branchId))
                .execute();
    }

    @Override
    public List<ParkingSpotResponseDto> getAllParkingSpotByBranchId(Long branchId) {
        return queryFactory
                .select(Projections.constructor(ParkingSpotResponseDto.class, parkingSpot.type, parkingSpot.x, parkingSpot.y))
                .from(parkingSpot)
                .where(parkingSpot.branch.branchId.eq(branchId))
                .fetch();
    }

    @Override
    public Point findCarLocationByCarNumber(String carNumber, SiDoType siDo, String branchName) {
        return queryFactory
                .select(Projections.constructor(Point.class, parkingSpot.x, parkingSpot.y))
                .from(parkingSpot)
                .where(parkingSpot.car.carNumber.eq(carNumber)
                        .and(parkingSpot.branch.eq(branchRepository.findBranchBySiDoAndBranchName(siDo, branchName))))
                .fetchOne();
    }
}