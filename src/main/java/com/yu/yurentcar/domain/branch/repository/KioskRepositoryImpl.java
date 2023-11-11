package com.yu.yurentcar.domain.branch.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yu.yurentcar.domain.branch.dto.BranchNameResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Repository;

import static com.yu.yurentcar.domain.branch.entity.QKiosk.kiosk;

@Log4j2
@Repository
@RequiredArgsConstructor
public class KioskRepositoryImpl implements KioskRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public BranchNameResponseDto findBranchNameByKioskId(Long kioskId) {
        return queryFactory.select(Projections.constructor(BranchNameResponseDto.class, kiosk.branch.siDo, kiosk.branch.branchName))
                .from(kiosk)
                .where(kiosk.kioskId.eq(kioskId))
                .fetchOne();
    }
}