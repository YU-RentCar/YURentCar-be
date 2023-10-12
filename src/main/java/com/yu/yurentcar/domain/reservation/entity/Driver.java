package com.yu.yurentcar.domain.reservation.entity;

import com.yu.yurentcar.domain.user.entity.DriverLicense;
import com.yu.yurentcar.global.BaseTimeEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "driver")
@Builder
@ToString
@AllArgsConstructor
public class Driver extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "driver_id")
    private Long driverId;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reservation_id")
    @ToString.Exclude
    private Reservation reservation;

    @NotNull
    @Column(name = "driver_name")
    private String driverName;

    @NotNull
    @Column(name = "birthdate")
    private LocalDateTime birthdate;

    @NotNull
    @Column(name = "phone_number", length = 15)
    private String phoneNumber;

    @NotNull
    @Column(name = "driver_license",length = 30)
    @Enumerated(EnumType.STRING)
    private DriverLicense driverLicense;

    @NotNull
    @Column(name = "driver_number")
    private String driverNumber;

    @NotNull
    @Column(name = "issue_date")
    private LocalDateTime issueDate;

    @NotNull
    @Column(name = "expiration_date")
    private LocalDateTime expirationDate;
}
