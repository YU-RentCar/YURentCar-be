package com.yu.yurentcar.domain.branch.entity;

import com.yu.yurentcar.domain.car.entity.Car;
import com.yu.yurentcar.global.BaseTimeEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@DynamicUpdate
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
    @Column(name = "type", length = 30)
    @Enumerated(EnumType.STRING)
    private ParkingSpotType type;

    @NotNull
    @Column
    private Double x;

    @NotNull
    @Column
    private Double y;

    public ParkingSpot updateState(ParkingSpotType parkingSpotType) {
        if (parkingSpotType != null)
        {
            if(parkingSpotType == ParkingSpotType.PARKINGSPOT_NO_CAR){
                this.car = null;
            }
            this.type = parkingSpotType;
        }
        return this;
    }

    public ParkingSpot updateCar(Car car, ParkingSpotType parkingSpotType) {
        if (car != null)
            this.car = car;
        if (parkingSpotType != null)
            this.type = parkingSpotType;
        return this;
    }
}
