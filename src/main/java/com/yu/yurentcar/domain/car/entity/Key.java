package com.yu.yurentcar.domain.car.entity;

import com.yu.yurentcar.domain.branch.entity.KeyStorage;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "key")
public class Key {
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

    //TODO : enum
    @NotNull
    @Column(name = "state", length = 30)
    private String state;
}
