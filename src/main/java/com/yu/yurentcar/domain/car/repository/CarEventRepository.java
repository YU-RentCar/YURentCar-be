package com.yu.yurentcar.domain.car.repository;

import com.yu.yurentcar.domain.car.entity.CarEvent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarEventRepository extends JpaRepository<CarEvent, Long>, CarEventRepositoryCustom {
}
