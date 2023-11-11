package com.yu.yurentcar.domain.branch.repository;

import com.yu.yurentcar.domain.branch.entity.Kiosk;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KioskRepository extends JpaRepository<Kiosk, Long>, KioskRepositoryCustom{
}
