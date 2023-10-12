package com.yu.yurentcar.domain.car.entity;

import com.yu.yurentcar.global.BaseTimeEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "carEvent")
public class CarEvent extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "car_event_id")
    private Long carEventId;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "car_id")
    private Car car;

    @NotNull
    @Column(name = "title")
    private String title;

    @NotNull
    @Column(name = "event_date")
    private LocalDateTime eventDate;

    @NotNull
    @Column(name = "content")
    private String content;

    @NotNull
    @Column(name = "is_repair")
    private Boolean isRepair;
}
