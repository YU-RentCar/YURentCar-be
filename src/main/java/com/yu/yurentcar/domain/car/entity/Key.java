package com.yu.yurentcar.domain.car.entity;

import com.yu.yurentcar.domain.branch.entity.KeyStorage;
import com.yu.yurentcar.global.BaseTimeEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "key")
@ToString
public class Key extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "key_id")
    private Long keyId;

    @ManyToOne
    @JoinColumn(name = "car_id")
    private Car car;

    @ManyToOne
    @JoinColumn(name = "key_storage_id")
    private KeyStorage keyStorage;

    @NotNull
    @Column(name = "rfid", length = 30)
    private String rfid;

    @Enumerated(EnumType.STRING)
    @NotNull
    @Column(name = "state", length = 30)
    private KeyState state;

    @Builder
    public Key(Car car, KeyStorage keyStorage, String rfid, KeyState keyState) {
        this.car = car;
        this.keyStorage = keyStorage;
        this.rfid = rfid;
        this.state = keyState;
    }

    public Key updateKey(Car car, KeyStorage keyStorage, String rfid, KeyState keyState) {
        this.car = car;
        this.keyStorage = keyStorage;
        this.rfid = rfid;
        this.state = keyState;
        return this;
    }
}
