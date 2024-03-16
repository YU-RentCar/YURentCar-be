package com.yu.yurentcar.domain.insurance.entity;


import com.yu.yurentcar.domain.reservation.entity.Reservation;
import com.yu.yurentcar.global.BaseTimeEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "insurance_contract")
@ToString
public class InsuranceContract extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "contract_id")
    private Long contractId;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "insurance_company_id")
    @ToString.Exclude
    private InsuranceCompany insuranceCompany;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reservation_id")
    @ToString.Exclude
    private Reservation reservation;

    @NotNull
    @Column(name = "contraction_price")
    private Integer contractionPrice;

    @NotNull
    @Column(name = "contraction_date")
    private LocalDateTime contractionDate;

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    public void setInsuranceCompany(InsuranceCompany insuranceCompany) {
        this.insuranceCompany = insuranceCompany;
    }

    @Builder
    public InsuranceContract(@NotNull InsuranceCompany insuranceCompany, @NotNull Reservation reservation, @NotNull Integer contractionPrice, @NotNull LocalDateTime contractionDate) {
        this.insuranceCompany = insuranceCompany;
        this.reservation = reservation;
        this.contractionPrice = contractionPrice;
        this.contractionDate = contractionDate;
    }
}
