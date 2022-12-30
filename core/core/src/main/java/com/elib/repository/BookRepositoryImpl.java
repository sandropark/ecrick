package com.elib.repository;

import com.elib.domain.QBook;
import com.elib.dto.BookListDto;
import com.elib.dto.QBookListDto;
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

import static org.springframework.util.StringUtils.hasText;

public class BookRepositoryImpl implements BookRepositoryCustom {

    private final JPAQueryFactory queryFactory;
    private final EntityManager em;

    public BookRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
        this.em = em;
    }

    @Override
    public Page<BookListDto> searchPage(String keyword, Pageable pageable) {
        List<BookListDto> content = queryFactory
                .select(new QBookListDto(QBook.book))
                .from(QBook.book)
                .where(bookContains(keyword))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(bookSort(pageable))
                .fetch();

        JPAQuery<Long> countQuery = queryFactory.select(QBook.book.count())
                .from(QBook.book)
                .where(bookContains(keyword));

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }

    private OrderSpecifier<?> bookSort(Pageable pageable) {
        if (pageable.getSort().isSorted()) {
            for (Sort.Order order : pageable.getSort()) {
                Order direction = order.getDirection().isAscending() ? Order.ASC : Order.DESC;
                switch (order.getProperty()) {
                    case "title":
                        return new OrderSpecifier<>(direction, QBook.book.title);
                    case "author":
                        return new OrderSpecifier<>(direction, QBook.book.author);
                    case "publisher":
                        return new OrderSpecifier<>(direction, QBook.book.publisher);
                    case "publicDate":
                        return new OrderSpecifier<>(direction, QBook.book.publicDate);
                }
            }
        }
        return new OrderSpecifier<>(Order.DESC, QBook.book.publicDate);
    }

    private BooleanExpression bookContains(String keyword) {
        return hasText(keyword)
                ? QBook.book.title.contains(keyword)
                .or(QBook.book.author.contains(keyword))
                .or(QBook.book.publisher.contains(keyword))
                : null;
    }
}
