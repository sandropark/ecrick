package com.ecrick.domain.repository;

import com.ecrick.domain.dto.BookListDto;
import com.ecrick.domain.dto.QBookListDto;
import com.ecrick.domain.dto.Search;
import com.ecrick.domain.entity.Book;
import com.ecrick.domain.service.SearchTarget;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.core.types.dsl.StringPath;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;

import static com.ecrick.domain.entity.QBook.book;
import static org.springframework.util.StringUtils.hasText;

public class BookRepositoryCustomImpl implements BookRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    private BookRepositoryCustomImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<BookListDto> searchPage(Search search, Pageable pageable) {
        List<BookListDto> content = queryFactory
                .select(
                        new QBookListDto(
                                book.id,
                                book.title,
                                book.author,
                                book.publisher,
                                book.publicDate,
                                book.coverUrl
                        )
                )
                .from(book)
                .where(bookContains(search))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(bookSort(pageable))
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(book.count())
                .from(book)
                .where(bookContains(search));

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }

    private BooleanExpression bookContains(Search search) {
        String keyword = search.getKeyword();
        if (!hasText(keyword))
            return null;

        SearchTarget searchTarget = search.getSearchTarget();

        switch (searchTarget) {
            case TITLE:
                return condition(book.title, keyword);
            case AUTHOR:
                return condition(book.author, keyword);
            case PUBLISHER:
                return condition(book.publisher, keyword);
            default:
                return condition(book.fullInfo, keyword);
        }
    }

    private BooleanExpression condition(StringPath target, String keyword) {
        return Expressions.numberTemplate(
                Integer.class, "function('match', {0}, {1})", target, '"' + keyword + '"'
        ).eq(1);
    }

    private OrderSpecifier[] bookSort(Pageable pageable) {
        return pageable
                .getSort()
                .get()
                .map(order -> new OrderSpecifier(getDirection(order), getTarget(order)))
                .toArray(OrderSpecifier[]::new);
    }

    private PathBuilder<?> getTarget(Sort.Order order) {
        return new PathBuilder<>(Book.class, "book").get(order.getProperty());
    }

    private Order getDirection(Sort.Order order) {
        return order.getDirection().isAscending() ? Order.ASC : Order.DESC;
    }

}
