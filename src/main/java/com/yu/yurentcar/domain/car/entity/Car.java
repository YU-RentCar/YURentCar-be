package com.yu.yurentcar.domain.car.entity;

import com.yu.yurentcar.domain.branch.entity.Branch;
import com.yu.yurentcar.domain.car.dto.CarRequestDto;
import com.yu.yurentcar.global.BaseTimeEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "car")
public class Car extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "car_id")
    private Long carId;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "car_spec")
    private CarSpecification carSpec;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "branch_id")
    private Branch branch;

    @NotNull
    @Column(name = "car_number", length = 10)
    private String carNumber;

    @NotNull
    @Column(name = "total_distance", columnDefinition = "int default 0")
    @Builder.Default
    private Integer totalDistance = 0;

    @Enumerated(EnumType.STRING)
    @NotNull
    @Column(name = "car_state", length = 10)
    private CarState carState;

    @NotNull
    @PositiveOrZero
    @Column(name = "car_price")
    private Integer carPrice;

    @NotNull
    @Max(value = 100)
    @Min(value = 0)
    @Column(name = "discount_rate")
    private Integer discountRate;

    @Column(name = "discount_reason")
    private String discountReason;

    @NotNull
    @Column(name = "car_description")
    private String carDescription;


    //    //에러 표기되지만 실제 동작에는 문제 없음
//    @Column(name = "repair_list", columnDefinition = "text[]")
//    private List<String> repairList;
//
//    //에러 표기되지만 실제 동작에는 문제 없음
//    @Column(name = "accident_list", columnDefinition = "text[]")
//    private List<String> accidentList;
    public Car updateCar(CarRequestDto carRequestDto, CarSpecification carSpecification, Branch branch) {
        this.carSpec = carSpecification;
        this.branch = branch;
        this.carNumber = carRequestDto.getCarNumber();
        this.totalDistance = carRequestDto.getTotalDistance();
        this.carPrice = carRequestDto.getCarPrice();
        this.discountRate = carRequestDto.getDiscountRate();
        this.discountReason = carRequestDto.getDiscountReason();
        this.carDescription = carRequestDto.getCarDescription();
        return this;
    }
}
