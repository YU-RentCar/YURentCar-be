package com.yu.yurentcar.domain.car.entity;

import com.yu.yurentcar.domain.car.entity.converter.TransmissionToBoolAttributeConverter;
import com.yu.yurentcar.domain.user.entity.CarSize;
import com.yu.yurentcar.domain.user.entity.OilType;
import com.yu.yurentcar.domain.user.entity.Transmission;
import com.yu.yurentcar.global.BaseTimeEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "car_specification")
@ToString
public class CarSpecification extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "car_spec_id")
    private Long carSpecId;

    @NotNull
    @Column(name = "car_name", length = 70)
    private String carName;

    @NotNull
    @Column(name = "max_passenger")
    private Integer maxPassenger;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "car_size", length = 30)
    private CarSize carSize;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "oil_type", length = 30)
    private OilType oilType;

    @NotNull
    @Convert(converter = TransmissionToBoolAttributeConverter.class)
    @Column(name = "is_auto_transmission", columnDefinition = "bool")
    private Transmission transmission;

    @NotNull
    @Column(name = "is_korean")
    private Boolean isKorean;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "car_brand", length = 30)
    private CarBrand carBrand;

    @NotNull
    @Column(name = "release_date")
    private LocalDateTime releaseDate;

    @Builder
    public CarSpecification(String carName, Integer maxPassenger, CarSize carSize, OilType oilType, Transmission transmission, CarBrand carBrand, LocalDateTime releaseDate) {
        this.carName = carName;
        this.maxPassenger = maxPassenger;
        this.carSize = carSize;
        this.oilType = oilType;
        this.transmission = transmission;
        this.carBrand = carBrand;
        this.isKorean = carBrand.getIsKorean();
        this.releaseDate = releaseDate;
    }
}
