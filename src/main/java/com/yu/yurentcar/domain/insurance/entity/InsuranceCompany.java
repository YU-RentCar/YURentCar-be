package com.yu.yurentcar.domain.insurance.entity;


import com.yu.yurentcar.global.BaseTimeEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "insurance_company")
@ToString
public class InsuranceCompany extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "company_id")
    private Long companyId;

    @NotNull
    @Column(name = "company_name", length = 40)
    private String companyName;

    @NotNull
    @Column(name = "phone_number", length = 15)
    private String phoneNumber;

    @NotNull
    @Column(name = "site_link", length = 100)
    private String siteLink;

    @Builder
    public InsuranceCompany(@NotNull String companyName, @NotNull String phoneNumber, @NotNull String siteLink) {
        this.companyName = companyName;
        this.phoneNumber = phoneNumber;
        this.siteLink = siteLink;
    }
}
