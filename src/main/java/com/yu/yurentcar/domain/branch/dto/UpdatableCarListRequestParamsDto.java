package com.yu.yurentcar.domain.branch.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UpdatableCarListRequestParamsDto {
    @NotNull
    private String adminUsername;
    @NotNull
    private Long reservationId;
    @NotNull
    //@JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime startDate;
    @NotNull
    //@JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime endDate;
}
