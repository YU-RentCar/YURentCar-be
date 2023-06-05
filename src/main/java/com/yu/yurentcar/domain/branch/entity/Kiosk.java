package com.yu.yurentcar.domain.branch.entity;

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
@Table(name = "kiosk")
public class Kiosk extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "kiosk_id")
    private Long kioskId;

    @NotNull
    @ManyToOne
    @JoinColumn(name="branch_id")
    private Branch branch;

    @NotNull
    @Column(name = "installation date")
    private LocalDateTime installationDate;

}
