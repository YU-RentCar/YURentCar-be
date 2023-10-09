package com.yu.yurentcar.domain.user.entity;

import com.yu.yurentcar.domain.branch.entity.Branch;
import com.yu.yurentcar.global.BaseTimeEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;


@Entity
@Getter
@NoArgsConstructor
@Table(name = "admin")
@ToString
public class Admin extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "admin_id")
    private Long adminId;

    // TODO : privilege class or enum 으로 대체 필요
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "privileges", length = 30)
    private Privileges privileges;

    @NotNull
    @Column(length = 50)
    private String username;

    @NotNull
    @Size(min = 64, max = 64)
    @Column(length = 64)
    private String password;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "branch_id")
    private Branch branch;

    @Builder
    public Admin(@NotNull Privileges privileges, @NotNull String username, @NotNull String password, Branch branch) {
        this.privileges = privileges;
        this.username = username;
        this.password = password;
        this.branch = branch;
    }
}
