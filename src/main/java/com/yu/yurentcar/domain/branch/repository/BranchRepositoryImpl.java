package com.yu.yurentcar.domain.branch.repository;


import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yu.yurentcar.global.SiDoType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

import static com.yu.yurentcar.domain.branch.entity.QBranch.branch;

@RequiredArgsConstructor
@Repository
public class BranchRepositoryImpl implements BranchRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<String> findBranchNameListBySiDo(String siDo) {
        JPAQuery<String> query = queryFactory.select(branch.branchName)
                 .from(branch);

        if(StringUtils.hasText(siDo))
            query.where(branch.siDo.eq(siDo));
        return query.orderBy(branch.branchName.asc()).fetch();
    }

}
