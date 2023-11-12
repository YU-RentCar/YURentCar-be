package com.yu.yurentcar.domain.car.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yu.yurentcar.domain.branch.repository.BranchRepository;
import com.yu.yurentcar.domain.car.dto.KeyManagementDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.yu.yurentcar.domain.car.entity.QCar.car;
import static com.yu.yurentcar.domain.car.entity.QKey.key;

@Log4j2
@RequiredArgsConstructor
@Repository
public class KeyRepositoryImpl implements KeyRepositoryCustom {
    private final BranchRepository branchRepository;

    private final JPAQueryFactory queryFactory;

    @Override
    public List<KeyManagementDto> findKeysByAdmin(String adminUsername) {
        return queryFactory
                .select(Projections.constructor(KeyManagementDto.class,
                        key.car.carNumber,
                        key.rfid,
                        key.state,
                        key.keyId,
                        key.keyStorage.kiosk.kioskId,
                        key.keyStorage.slotNumber))
                .from(key)
                .where(key.car.carId.in(queryFactory
                        .select(car.carId)
                        .from(car)
                        .where(car.branch.eq(branchRepository.findBranchByAdmin(adminUsername)))))
                .fetch();
    }
}