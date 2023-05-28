package com.yu.yurentcar.domain.branch.entity;

import com.yu.yurentcar.BaseTimeEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "branch")
public class Branch extends BaseTimeEntity {
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
    private String siDo;

    @NotNull
    @Column(name = "gu_gun",  length = 50)
    private String guGun;

    @NotNull
    @Column(name = "detail_address",  length = 50)
    private String detailAddress;

    @NotNull
    @Column(name = "telephone_number", length = 30)
    private String telephoneNumber;

    @NotNull
    @Column(name = "boss_name", length = 40)
    private String bossName;

    @NotNull
    @Column(name = "number_of_car")
    @Builder.Default
    private Integer numberOfCar = 0;

    @NotNull
    @Column(name = "establishment_date")
    private LocalDateTime establishmentDate;
}
