package com.yu.yurentcar.domain.branch.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "branch")
public class Branch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "branch_id")
    private Long branchId;

    @NotNull
    @Column(name = "branch_name", length = 30)
    private String branchName;

    @NotNull
    @Column(name = "branch_address")
    private String branchAddress;

    @NotNull
    @Column(name = "si_do", length = 50)
    private String si_do;

    @NotNull
    @Column(name = "gu_gun",  length = 50)
    private String gu_gun;

    @NotNull
    @Column(name = "branch_detail_address",  length = 50)
    private String branchDetailAddress;

    @NotNull
    @Column(name = "branch_telephone_number", length = 30)
    private String branchTelephoneNumber;

    @NotNull
    @Column(name = "branch_boss_name", length = 40)
    private String branchBossName;

    @NotNull
    @Column(name = "number_of_car")
    @Builder.Default
    private Integer numberOfCar = 0;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    @Column(name = "establishment_date")
    private LocalDateTime establishmentDate;
}
