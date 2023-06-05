package com.yu.yurentcar.domain.branch.entity;

import com.yu.yurentcar.global.BaseTimeEntity;
import com.yu.yurentcar.global.SiDoType;
import com.yu.yurentcar.global.SiDoTypeToStringAttributeConverter;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "branch")
@ToString
public class Branch extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "branch_id")
    private Long branchId;

    @NotNull
    @Column(name = "branch_name", length = 30)
    private String branchName;

    @NotNull
    @Column(name = "address_latitude")
    private Double latitude;

    @NotNull
    @Column(name = "address_longitude")
    private Double longitude;

    @NotNull
    @Convert(converter = SiDoTypeToStringAttributeConverter.class)
    @Column(name = "si_do", length = 50)
    private SiDoType siDo;

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
