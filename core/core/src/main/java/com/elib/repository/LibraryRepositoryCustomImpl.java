package com.elib.repository;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;

import javax.persistence.EntityManager;
import java.util.List;

import static com.elib.domain.QLibrary.library;

public class LibraryRepositoryCustomImpl implements LibraryRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    private LibraryRepositoryCustomImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<String> findNamesAll() {
        return queryFactory.select(Expressions.stringTemplate(
                        "function('regexp_replace', {0}, {1}, {2})",
                        library.name, "\\(.+\\)", "")).distinct()
                .from(library)
                .orderBy(new OrderSpecifier<>(Order.ASC, library.name))
                .fetch();
    }
}
