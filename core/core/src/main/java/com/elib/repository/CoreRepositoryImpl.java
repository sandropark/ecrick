package com.elib.repository;

import com.elib.domain.Core;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.function.Supplier;

import static com.elib.domain.QCore.core;

public class CoreRepositoryImpl implements CoreRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    private CoreRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public boolean isAllBookIdNull() {
        Long count = queryFactory
                .select(core.book.count())
                .from(core)
                .fetchOne();
        return count == 0;
    }

    @Override
    public List<Core> findNewAll() {
        return queryFactory.selectFrom(core)
                .where(core.book.isNull())
                .fetch();
    }

    private BooleanBuilder titleEq(Tuple tuple) {
        return nullSafeBuilder(() -> core.title.eq(tuple.get(core.title)));
    }

    private static BooleanBuilder nullSafeBuilder(Supplier<BooleanExpression> f) {
        try {
            return new BooleanBuilder(f.get());
        } catch (IllegalArgumentException e) {
            return new BooleanBuilder();
        }
    }

}
