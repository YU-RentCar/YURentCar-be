package com.yu.yurentcar.domain.insurance.entity;


import com.yu.yurentcar.domain.reservation.entity.Reservation;
import com.yu.yurentcar.global.BaseTimeEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "insurance_contract")
public class InsuranceContract extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "contract_id")
    private Long contractId;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "insurance_company_id")
    private InsuranceCompany insuranceCompany;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reservation_id")
    private Reservation reservation;

    @NotNull
    @Column(name = "contraction_price")
    private Integer contractionPrice;

    @NotNull
    @Column(name = "contraction_date")
    private LocalDateTime contractionDate;
}
