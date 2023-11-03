package com.yu.yurentcar.domain.branch.repository;

import com.yu.yurentcar.domain.branch.entity.KeyStorage;

public interface KeyStorageRepositoryCustom {
    KeyStorage findKeyStorageByReservationId(Long reservationId, Long kioskId);
    KeyStorage findUsableSlotByKioskId(Long kioskId);
}