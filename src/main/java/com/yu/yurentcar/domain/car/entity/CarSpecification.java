package com.yu.yurentcar.domain.car.entity;

import com.yu.yurentcar.BaseTimeEntity;
import com.yu.yurentcar.domain.car.entity.converter.TransmissionToBoolAttributeConverter;
import com.yu.yurentcar.domain.user.entity.CarSize;
import com.yu.yurentcar.domain.user.entity.OilType;
import com.yu.yurentcar.domain.user.entity.Transmission;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "car_specification")
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
    @Builder.Default
    private Boolean isKorean = this.carBrand.getIsKorean();

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "car_brand", length = 30)
    private CarBrand carBrand;

    @NotNull
    @Column(name = "release_date")
    private LocalDateTime releaseDate;
}
