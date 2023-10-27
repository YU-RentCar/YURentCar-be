package com.yu.yurentcar.domain.car.repository;

import com.yu.yurentcar.domain.branch.entity.KeyStorage;
import com.yu.yurentcar.domain.car.entity.Key;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface KeyRepository extends JpaRepository<Key, Long> {
    Optional<Key> findByRfid(String rfid);
    Optional<Key> findByKeyStorage(KeyStorage keyStorage);
}