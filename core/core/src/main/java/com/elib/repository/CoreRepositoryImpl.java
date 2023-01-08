package com.elib.repository;

import com.elib.domain.Core;
import com.elib.dto.CoreListDto;
import com.elib.dto.QCoreListDto;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.support.PageableExecutionUtils;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.function.Supplier;

import static com.elib.domain.QCore.core;
import static org.springframework.util.StringUtils.hasText;

public class CoreRepositoryImpl implements CoreRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    private CoreRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<CoreListDto> searchPage(String keyword, Pageable pageable) {
        List<CoreListDto> content = queryFactory
                .select(new QCoreListDto(core))
                .from(core)
                .where(bookContains(keyword))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(bookSort(pageable))
                .fetch();

        JPAQuery<Long> countQuery = queryFactory.select(core.count())
                .from(core)
                .where(bookContains(keyword));

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }

    private OrderSpecifier<?> bookSort(Pageable pageable) {
        if (pageable.getSort().isSorted()) {
            for (Sort.Order order : pageable.getSort()) {
                Order direction = order.getDirection().isAscending() ? Order.ASC : Order.DESC;
                switch (order.getProperty()) {
                    case "title":
                        return new OrderSpecifier<>(direction, core.title);
                    case "author":
                        return new OrderSpecifier<>(direction, core.author);
                    case "publisher":
                        return new OrderSpecifier<>(direction, core.publisher);
                    case "publicDate":
                        return new OrderSpecifier<>(direction, core.publicDate);
                }
            }
        }
        return new OrderSpecifier<>(Order.DESC, core.publicDate);
    }

    private BooleanExpression bookContains(String keyword) {
        return hasText(keyword)
                ? core.title.contains(keyword)
                .or(core.author.contains(keyword))
                .or(core.publisher.contains(keyword))
                : null;
    }

    private BooleanBuilder titleEq(Core entity) {
        return nullSafeBuilder(() -> core.title.eq(entity.getTitle()));
    }

    private static BooleanBuilder nullSafeBuilder(Supplier<BooleanExpression> f) {
        try {
            return new BooleanBuilder(f.get());
        } catch (IllegalArgumentException e) {
            return new BooleanBuilder();
        }
    }

}
