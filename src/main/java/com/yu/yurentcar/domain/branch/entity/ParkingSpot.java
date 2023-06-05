package com.yu.yurentcar.domain.branch.entity;

import com.yu.yurentcar.domain.car.entity.Car;
import com.yu.yurentcar.global.BaseTimeEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "parking_spot")
public class ParkingSpot extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "parking_spot_id")
    private Long parkingSpotId;

    @ManyToOne
    @JoinColumn(name = "branch_id")
    private Branch branch;

    @ManyToOne
    @JoinColumn(name = "car_id")
    private Car car;

    @NotNull
    @Column(name="is_parking")
    private Boolean isParking;

    @NotNull
    @Column
    private Double x;

    @NotNull
    @Column
    private Double y;
}
