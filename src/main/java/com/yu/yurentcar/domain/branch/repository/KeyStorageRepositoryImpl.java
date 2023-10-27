package com.yu.yurentcar.domain.branch.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yu.yurentcar.domain.branch.entity.KeyStorage;
import com.yu.yurentcar.domain.car.entity.KeyState;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.yu.yurentcar.domain.branch.entity.QKeyStorage.keyStorage;
import static com.yu.yurentcar.domain.car.entity.QKey.key;
import static com.yu.yurentcar.domain.reservation.entity.QReservation.reservation;

@RequiredArgsConstructor
@Repository
public class KeyStorageRepositoryImpl implements KeyStorageRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public KeyStorage findKeyStorageByReservationId(Long reservationId, Long kioskId) {
        return queryFactory.select(keyStorage)
                .from(key)
                .innerJoin(key.keyStorage, keyStorage)
                // 예약된 차량의 차키들 검색
                .where(key.car.in(queryFactory.select(reservation.car)
                                .from(reservation)
                                .where(reservation.reservationId.eq(reservationId)))
                        // 키 상태가 대기 중인 것
                        .and(key.state.eq(KeyState.WAITING))
                        // 키오스크 아이디가 같은 것
                        .and(keyStorage.kiosk.kioskId.eq(kioskId)))
                .fetchFirst();
    }

    @Override
    public KeyStorage findUsableSlotByKioskId(Long kioskId) {
        return queryFactory.selectFrom(keyStorage)
                .where(keyStorage.isAvailable.isTrue()
                        .and(keyStorage.kiosk.kioskId.eq(kioskId)))
                .orderBy(keyStorage.slotNumber.asc())
                .fetchFirst();
    }
}