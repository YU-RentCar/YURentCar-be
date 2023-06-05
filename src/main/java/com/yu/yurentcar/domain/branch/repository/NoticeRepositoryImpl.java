package com.yu.yurentcar.domain.branch.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yu.yurentcar.domain.branch.dto.NoticeResponseDto;
import com.yu.yurentcar.global.SiDoType;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

import static com.yu.yurentcar.domain.branch.entity.QBranch.branch;
import static com.yu.yurentcar.domain.branch.entity.QNotice.notice;

@Log4j2
@Repository
@RequiredArgsConstructor
public class NoticeRepositoryImpl implements NoticeRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<NoticeResponseDto> getNoticesByBranchName(SiDoType siDo, String branchName) {
        //notice 조회
        //지점네임을 던져줌
        //지점네임과 지점 관할구역으로 지점id 조회
        //notice테이블을 지점id로 걸러냄
        //종료일이 현재보다 뒤인 경우 혹은 종료일이 없는 경우만 남겨냄
        return queryFactory
                .select(Projections.constructor(NoticeResponseDto.class, notice.noticeId,notice.title,notice.finishDate,notice.createdAt,notice.modifiedAt))
                .from(notice)
                .where(notice.branch.eq(queryFactory
                        .selectFrom(branch)
                        .where(branch.branchName.eq(branchName).and(branch.siDo.eq(siDo))))
                        .and(notice.finishDate.gt(LocalDateTime.now()).or(notice.finishDate.isNull())))
                .orderBy(notice.modifiedAt.desc())
                .fetch();
    }

}
