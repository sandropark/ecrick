package com.elib.repository;

import com.elib.domain.Book;
import com.elib.domain.Core;
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

import static com.elib.domain.QBook.book;
import static org.springframework.util.StringUtils.hasText;

public class BookRepositoryCustomImpl implements BookRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    private BookRepositoryCustomImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<BookListDto> searchPage(String keyword, Pageable pageable) {
        List<BookListDto> content = queryFactory
                .select(new QBookListDto(book))
                .from(book)
                .where(bookContains(keyword))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(bookSort(pageable))
                .fetch();

        JPAQuery<Long> countQuery = queryFactory.select(book.count())
                .from(book)
                .where(bookContains(keyword));

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }

    private OrderSpecifier<?> bookSort(Pageable pageable) {
        if (pageable.getSort().isSorted()) {
            for (Sort.Order order : pageable.getSort()) {
                Order direction = order.getDirection().isAscending() ? Order.ASC : Order.DESC;
                switch (order.getProperty()) {
                    case "title":
                        return new OrderSpecifier<>(direction, book.title);
                    case "author":
                        return new OrderSpecifier<>(direction, book.author);
                    case "publisher":
                        return new OrderSpecifier<>(direction, book.publisher);
                    case "publicDate":
                        return new OrderSpecifier<>(direction, book.publicDate);
                }
            }
        }
        return new OrderSpecifier<>(Order.DESC, book.publicDate);
    }

    private BooleanExpression bookContains(String keyword) {
        return hasText(keyword)
                ? book.title.contains(keyword)
                .or(book.author.contains(keyword))
                .or(book.publisher.contains(keyword))
                : null;
    }

    @Override
    public boolean isEmpty() {
        Long count = queryFactory.select(book.count())
                .from(book)
                .fetchOne();
        return count == 0;
    }

    @Override
    public Book findByCore(Core core) {
        return queryFactory.selectFrom(book)
                .where(book.title.eq(core.getTitle())
                        .and(book.author.eq(core.getAuthor()))
                        .and(book.publisher.eq(core.getPublisher()))
                )
                .fetchOne();
    }

}
