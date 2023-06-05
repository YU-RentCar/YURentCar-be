package com.yu.yurentcar.domain.car.repository;

import com.yu.yurentcar.domain.car.entity.CarSpecification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarSpecificationRepository extends JpaRepository<CarSpecification, Long> {
}