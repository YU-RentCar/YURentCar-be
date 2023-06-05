package com.yu.yurentcar.domain.branch.entity;

import com.yu.yurentcar.domain.user.entity.Admin;
import com.yu.yurentcar.global.BaseTimeEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@DynamicUpdate
@DynamicInsert
@Table(name = "notice")
public class Notice extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notice_id")
    private Long noticeId;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "branch_id")
    private Branch branch;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_id")
    private Admin admin;

    @NotNull
    @Column(name = "title", length = 100)
    private String title;

    @NotNull
    @Column(name = "description")
    private String description;

    @Column(name = "photo_url", length = 200)
    private String photoUrl;

    @Column(name = "start_date")
    private LocalDateTime startDate;

    @Column(name = "finish_date")
    private LocalDateTime finishDate;

    @Builder
    public Notice(Long noticeId, @NotNull Branch branch, @NotNull Admin admin, @NotNull String title, @NotNull String description, String photoUrl, LocalDateTime startDate, LocalDateTime finishDate) {
        this.noticeId = noticeId;
        this.branch = branch;
        this.admin = admin;
        this.title = title;
        this.description = description;
        this.photoUrl = photoUrl;
        this.startDate = startDate;
        this.finishDate = finishDate;
    }
}
