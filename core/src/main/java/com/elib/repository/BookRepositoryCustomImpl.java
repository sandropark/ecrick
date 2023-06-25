package com.elib.repository;

import com.elib.domain.Book;
import com.elib.dto.BookListDto;
import com.elib.dto.QBookListDto;
import com.elib.dto.Search;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.support.PageableExecutionUtils;

import javax.persistence.EntityManager;
import java.util.List;

import static com.elib.domain.QBook.book;
import static org.springframework.util.StringUtils.hasText;

public class BookRepositoryCustomImpl implements BookRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    private BookRepositoryCustomImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<BookListDto> searchPage(Search search, Pageable pageable) {
        String keyword = search.getKeyword();

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
                .where(bookContains(keyword))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(bookSort(pageable))
                .fetch();

        JPAQuery<Long> countQuery = queryFactory
                .select(book.count())
                .from(book)
                .where(bookContains(keyword));

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }

    private BooleanExpression bookContains(String keyword) {
        if (hasText(keyword))
            return Expressions.numberTemplate(
                    Integer.class, "function('match', {0}, {1})", book.fullInfo, keyword
            ).eq(1);
        return null;
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
