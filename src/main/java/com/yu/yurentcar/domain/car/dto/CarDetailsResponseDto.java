package com.yu.yurentcar.domain.car.dto;

import com.yu.yurentcar.domain.car.entity.CarBrand;
import com.yu.yurentcar.domain.user.entity.CarSize;
import com.yu.yurentcar.domain.user.entity.OilType;
import com.yu.yurentcar.domain.user.entity.Transmission;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;

import java.time.LocalDateTime;

@Log4j2
@Getter
@ToString
public class CarDetailsResponseDto {
    private final String photoUrl;
    private final String carName;
    private final String carNumber;
    private final String carDescription;
    private final Integer totalDistance;
    private final String carSize;
    private final String oilType;
    private final LocalDateTime releaseDate;
    private final LocalDateTime createdAt;
    private final String transmission;
    private final Integer maxPassenger;
    private final Integer beforePrice;
    private final Integer afterPrice;
    private final Integer discountRate;
    private final String discountReason;
    private final String carBrand;
    private final Boolean isKorean;

    public CarDetailsResponseDto(String carDescription, Integer totalDistance, LocalDateTime releaseDate, LocalDateTime createdAt, Integer carPrice, Integer discountRate, String discountReason, String carName, String carNumber, CarSize carSize, OilType oilType, Transmission transmission, Integer maxPassenger, String photoUrl, CarBrand carBrand, Boolean isKorean) {
        this.carDescription = carDescription;
        this.totalDistance = totalDistance;
        this.releaseDate = releaseDate;
        this.createdAt = createdAt;
        this.beforePrice = carPrice;
        this.afterPrice = (int) Math.ceil((beforePrice - (beforePrice * discountRate * 0.01)));
        this.discountRate = discountRate;
        this.discountReason = discountReason;
        this.carName = carName;
        this.carNumber = carNumber;
        this.carSize = carSize.getDesc();
        this.oilType = oilType.getDesc();
        this.transmission = transmission.getDesc();
        this.maxPassenger = maxPassenger;
        this.photoUrl = photoUrl;
        this.carBrand = carBrand.getDesc();
        this.isKorean = isKorean;
    }

}
