package com.yu.yurentcar.domain.car.repository;

import com.yu.yurentcar.domain.car.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CarRepository extends JpaRepository<Car, Long>, CarRepositoryCustom {

    Optional<Car> findByCarId(Long carId);
    Optional<Car> findByCarNumber(String carNumber);
}