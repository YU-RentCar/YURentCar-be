package com.yu.yurentcar.domain.car.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "car")
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "car_id")
    private Long carId;

    @NotNull
    @Column(name = "car_number", length = 10)
    private String carNumber;

    @NotNull
    @Column(name = "total_distance")
    private Integer totalDistance;

    @NotNull
    @Column(name = "car_state", length = 10)
    private String carState;

    //TODO : 배열 연동
    @ElementCollection
    @Column(name = "repair_list", length = 1000)
    private List<String> repairList;

    //TODO : 배열 연동
    @ElementCollection
    @Column(name = "accident_list", length = 1000)
    private List<String> accidentList;
}
