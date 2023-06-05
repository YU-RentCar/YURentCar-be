package com.yu.yurentcar.domain.branch.entity;

import com.yu.yurentcar.global.BaseTimeEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "key_storage")
public class KeyStorage extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "key_storage_id")
    private Long keyStorageId;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "kiosk_id")
    private Kiosk kiosk;

    @NotNull
    @Column(name="slot_number")
    private Long slotNumber;

    @NotNull
    @Column(name="is_available")
    private Boolean isAvailable;
}
