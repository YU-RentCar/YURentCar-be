package com.yu.yurentcar.domain.insurance.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "insurance_company")
public class InsuranceCompany {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "company_id")
    private Long companyId;

    @NotNull
    @Column(name = "company_name",length = 40)
    private String companyName;

    @NotNull
    @Column(name = "company_telephone_number",length = 15)
    private String companyTelephoneNumber;

    @NotNull
    @Column(name = "company_site_link",length = 100)
    private String companySiteLink;
}
