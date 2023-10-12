package com.yu.yurentcar.domain.car.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CarRequestDto {
    @NotNull(message = "차량번호가 비었습니다.")
    private String carNumber;
    @NotNull(message = "총 주행거리가 비었습니다.")
    private Integer totalDistance;
    @NotNull(message = "가격이 비었습니다.")
    private Integer carPrice;
    @NotNull(message = "할인율이 비었습니다.")
    private Integer discountRate;
    private String discountReason;
    @NotNull(message = "차량 설명이 비었습니다.")
    private String carDescription;
    @NotNull(message = "차량이름이 비었습니다.")
    private String carName;
    @NotNull(message = "최대 탑승 인원이 비었습니다.")
    private Integer maxPassenger;
    @NotNull(message = "차량 크기가 비었습니다.")
    private String carSize;
    @NotNull(message = "유종이 비었습니다.")
    private String oilType;
    @NotNull(message = "구동기가 비었습니다.")
    private String transmission;
    private boolean isKorean;
    @NotNull(message = "브랜드가 비었습니다.")
    private String carBrand;
    @NotNull(message = "차량 출시일이 비었습니다.")
    private LocalDateTime releaseDate;
    private List<CarRepairEventDto> repairList;
    private List<CarAccidentEventDto> accidentList;
}