package com.yu.yurentcar.domain.user.entity;

import com.yu.yurentcar.domain.branch.entity.Branch;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "admin")
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "admin_id")
    private Long adminId;

    @NotNull
    @Column(name = "privileges", length = 30)
    private String privileges;

    @NotNull
    @Column(name = "id", length = 50)
    private String id;

    @NotNull
    @Size(min = 256,max=256)
    @Column(name = "password", length = 256)
    private String password;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "branch_id")
    private Branch branchId;
}
