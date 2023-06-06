package com.yu.yurentcar.domain.branch.repository;


import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yu.yurentcar.global.SiDoType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.yu.yurentcar.domain.branch.entity.QBranch.branch;

@RequiredArgsConstructor
@Repository
public class BranchRepositoryImpl implements BranchRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<String> findBranchNameListBySiDo(SiDoType siDo) {
        JPAQuery<String> query = queryFactory.select(branch.branchName)
                 .from(branch);

        if(siDo != null)
            query.where(branch.siDo.eq(siDo));
        return query.orderBy(branch.branchName.asc()).fetch();
    }

    @Override
    public Point getGeoPointByBranchName(SiDoType siDo, String branchName) {
        return queryFactory.select(Projections.constructor(Point.class, branch.latitude, branch.longitude))
                .from(branch)
                .where(branch.siDo.eq(siDo).and(branch.branchName.eq(branchName))).fetchFirst();
    }
}
