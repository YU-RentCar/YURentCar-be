package com.yu.yurentcar.domain.reservation.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@ToString
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class DriverDto {
    private String name;
    @JsonFormat(pattern = "yyyy. MM. dd. HH:mm")
    private LocalDateTime birthdate;
    private String phoneNumber;
    private String licenseType;
    private String licenseNumber;
    @JsonFormat(pattern = "yyyy. MM. dd. HH:mm")
    private LocalDateTime issueDate;
    @JsonFormat(pattern = "yyyy. MM. dd. HH:mm")
    private LocalDateTime expirationDate;
}