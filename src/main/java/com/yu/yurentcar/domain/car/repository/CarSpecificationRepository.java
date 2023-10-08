package com.yu.yurentcar.domain.car.repository;

import com.yu.yurentcar.domain.car.entity.CarBrand;
import com.yu.yurentcar.domain.car.entity.CarSpecification;
import com.yu.yurentcar.domain.user.entity.OilType;
import com.yu.yurentcar.domain.user.entity.Transmission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface CarSpecificationRepository extends JpaRepository<CarSpecification, Long> {
    Optional<CarSpecification> findByCarBrandAndCarNameAndOilTypeAndReleaseDateAndTransmission(
            CarBrand carBrand,
            String carName,
            OilType oilType,
            LocalDateTime releaseDate,
            Transmission transmission);
}