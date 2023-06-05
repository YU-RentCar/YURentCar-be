package com.yu.yurentcar.domain.car.dto;

import com.yu.yurentcar.domain.user.entity.CarSize;
import com.yu.yurentcar.domain.user.entity.OilType;
import com.yu.yurentcar.domain.user.entity.Transmission;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Getter
@ToString
public class CarDetailsResponseDto {
    private final Integer price;
    private final String carName;
    private final String carNumber;
    private final String carSize;
    private final String oilType;
    private final String transmission;
    private final Integer maxPassenger;
    private final String imageUri;

    public CarDetailsResponseDto(String carName, String carNumber, CarSize carSize, OilType oilType, Transmission transmission, Integer maxPassenger) {
        this.price = 5000;
        this.carName = carName;
        this.carNumber = carNumber;
        this.carSize = carSize.getDesc();
        this.oilType = oilType.getDesc();
        this.transmission = transmission.getDesc();
        this.maxPassenger = maxPassenger;
        this.imageUri = "http://";
    }

}
