package com.yu.yurentcar.domain.branch.repository;

import com.yu.yurentcar.domain.branch.entity.KeyStorage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface KeyStorageRepository extends JpaRepository<KeyStorage, Long>, KeyStorageRepositoryCustom {
    Optional<KeyStorage> findBySlotNumberAndKiosk_KioskId(Long slotNumber, Long kioskId);
}