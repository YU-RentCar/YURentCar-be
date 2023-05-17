package com.yu.yurentcar.domain.car.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "car_specification")
public class CarSpecification {
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

    //TODO : enum
    @NotNull
    @Column(name = "car_size", length = 30)
    private String carSize;

    //TODO : enum
    @NotNull
    @Column(name = "type_of_oil", length = 30)
    private String typeOfOil;

    @NotNull
    @Column(name = "is_auto")
    private Boolean isAuto;

    @NotNull
    @Column(name = "is_korean")
    private Boolean isKorean;

    //TODO : enum
    @NotNull
    @Column(name = "car_brand", length = 30)
    private String carBrand;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @Column(name = "release_date")
    private LocalDateTime releaseDate;
}
